<h1>Generate Yolo2 config for custom dataset.</h1>
bind variable values.<br>
<p>Line 7: <b><i>classes=20</b></i>, the number of categories we want to detect</p>
<p>Line 8: <b><i>batch = 64</b></i>, this means we will be using 64 images for every training step</p>
<p>Line 9: <b><i>subdivisions = 8</b></i>, he batch will be divided by 8 to decrease GPU VRAM requirements. The training step will throw a CUDA out of memory error so you can adjust accordingly.</p>
<p>Run python Yolov2Generate.py </p>

<h1>C ++ A wrapper for the Darknet detector.</h1>
<p>Install OpenCV.</p>
<p>Create a <b><i>YoloV2</b></i> object with parameters <b><i>thresh</b></i> and<b><i> hier_thresh</b></i>. Parameters <b><i>thresh</b></i> and <b><i>hier_thresh</b></i> thresholds of probability.</p>
<p>Load the network by passing the configuration file addresses to the parameters by calling method <b><i>loadNet(const std::string & model_prototxt, const std::string & model_net, const std::string & model_class)</b></i>.</p>
<p>To process images, call the <b><i>imgProcess (cv :: Mat img)</b></i> method. </p>