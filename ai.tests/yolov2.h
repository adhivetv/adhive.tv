#ifndef YOLOV2_H
#define YOLOV2_H

#include <darknet.h>
#include <string>

class YoloV2 
{
public:
    YoloV2(const float & _thresh, const float & _hier_thresh);
    ~YoloV2();
    virtual void loadNet(const std::string & model_prototxt, const std::string & model_net, const std::string & model_class);
    virtual std::multimap<std::string, std::vector<float> > imgProcess(cv::Mat img);
    virtual void SetPermission(std::string name, std::string value);

private:
    std::multimap<std::string, std::vector<float> > interpret_output(float **probs, box *boxes, int num, int clasess, image im);
    int max_index(float *probs, int clasess);
    image MatToImage(cv::Mat img);

    network net;
    layer l;
    float **probs;
    box *boxes;
    float hier_thresh;
    float thresh;

    config cfg;

    std::vector<std::string> labels_;

};

#endif // YOLOV2_H
