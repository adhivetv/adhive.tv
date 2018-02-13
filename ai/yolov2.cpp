#include "yolov2.h"

#include <cmath>

YoloV2::YoloV2(const float & _thresh, const float & _hier_thresh)
{
    probs = NULL;
	thresh = _thresh;
	hier_thresh = _hier_thresh;
}

YoloV2::~YoloV2()
{
    free_ptrs((void **)probs, l.w*l.h*l.n);
}

void YoloV2::loadNet(const std::string & model_prototxt, const std::string & model_net, const std::string & model_class)
{
    try{
        cfg.openConfig(name, base);
#ifdef GPU
        cuda_set_device(1);
#endif

        char *filename=(char*)calloc(model_prototxt.size(), sizeof(char));
        strcpy (filename, model_prototxt.c_str());
        net = parse_network_cfg(filename);

        free(filename);
        filename=(char*)calloc(model_net.size(), sizeof(char));
        strcpy (filename, model_net.c_str());
        load_weights(&net, filename);

        set_batch_network(&net, 1);
        srand(2222222);
        l = net.layers[net.n-1];
        boxes = (box*) calloc(l.w*l.h*l.n, sizeof(box));
        probs = (float**) calloc(l.w*l.h*l.n, sizeof(float *));

        std::ifstream labels(model_class);
        std::string line;
        while (std::getline(labels, line))
            labels_.push_back(std::string(line));
    }catch(...){

    }
}

std::multimap<std::string, std::vector<float> > YoloV2::imgProcess(cv::Mat img)
{
    try{
        float **masks = 0;
        int j;

        for(j = 0; j < l.w*l.h*l.n; ++j) probs[j] = (float*)calloc(l.classes + 1, sizeof(float *));
        if (l.coords > 4){
            masks = (float**)calloc(l.w*l.h*l.n, sizeof(float*));
            for(j = 0; j < l.w*l.h*l.n; ++j) masks[j] = (float*)calloc(l.coords-4, sizeof(float *));
        }
        image im = MatToImage(img);
        image sized = letterbox_image(im, net.w, net.h);
        float *X = sized.data;

        network_predict(net, X);
        get_region_boxes(l, im.w, im.h, net.w, net.h, thresh, probs, boxes, masks, 0, 0, hier_thresh, 1);

        float nms=.3;
        if (nms) do_nms_obj(boxes, probs, l.w*l.h*l.n, l.classes, nms);
        else if (nms) do_nms_sort(boxes, probs, l.w*l.h*l.n, l.classes, nms);
        std::multimap<std::string, std::vector<float> > out= interpret_output(probs, boxes, l.w*l.h*l.n, l.classes, im);

        free_image(im);
        free_image(sized);

        return out;
    }catch(std::exception& e){
        std::cout<< "exception caught: " << e.what() << '\n';
        return std::multimap<std::string, std::vector<float> >();
    }
}

void YoloV2::SetPermission(std::string name, std::string value)
{
    if(name=="thresh")
        hier_thresh=std::stoi(value);
}

int YoloV2::max_index(float *probs, int clasess){
    int max=0;
    for(int i=0; i<clasess;++i){
        if(probs[max] < probs[i])
            max = i;
    }
    return max;
}

std::multimap<std::string, std::vector<float> > YoloV2::interpret_output(float **probs, box *boxes, int num, int clasess, image im){
    std::multimap<std::string, std::vector<float> > out;
    for(int i = 0; i < num; ++i){
        int classId = max_index(probs[i], clasess);
        float prob = probs[i][classId];
        if(prob > thresh){
            std::vector<float> buf;
            buf.push_back(prob);

            box b = boxes[i];

            int left  = (b.x-b.w/2.)*im.w;
            int right = (b.x+b.w/2.)*im.w;
            int top   = (b.y-b.h/2.)*im.h;
            int bot   = (b.y+b.h/2.)*im.h;

            if(left < 0) left = 0;
            if(right > im.w-1) right = im.w-1;
            if(top < 0) top = 0;
            if(bot > im.h-1) bot = im.h-1;

            buf.push_back(left);
            buf.push_back(top);
            buf.push_back(right);
            buf.push_back(bot);
            if(labels_.size()>classId)
                out.insert(std::make_pair( std::string(labels_[classId]), buf ) );
        }
    }
    return out;
}


image YoloV2::MatToImage(cv::Mat img){
    image im;
    unsigned char *data = (unsigned char *)img.data;

    int h = img.rows;
    int w = img.cols;
    int c = img.channels();
    im = make_image(w,h,c);

    int step = w*c;

    for(unsigned int i = 0; i < h; ++i){
        for(unsigned int k= 0; k < c; ++k){
            for(unsigned int j = 0; j < w; ++j){
                im.data[k*w*h + i*w + j] = data[i*step + j*c + (2-k)]/255.;
            }
        }
    }


    return im;
}
