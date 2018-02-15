# adhive.tv

<ul>
<li><a href="https://github.com/adhivetv/adhive.tv">Background</a></li>(#Background)
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/RabbitMQ">RabbitMQ</a>
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/COMPFRAMEWORK">COMPFRAMEWORK</a> 
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/SMART-SENDER">SMART-SENDER</a>
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/ai">AI</a>
<ul>
</ul>
 
<a name="Background"></a><h1>Distributed Multi-Layered AI Cluster Architecture</h1>
 
<p>The market has been plagued by various issues related to correct identification and placement of content. The main problem at the moment is video and photo file recognition from various internet sources without losing data components.</p>
 
<p>The solution in this regard is to ensure the performance and fault tolerance of recognition functions. To solve the issue and bring high quality content to the market, AdHive has developed a distributed multi-layered AI cluster architecture. The development of the AI cluster ensures the performance of the recognition functions and guarantees its fault tolerance.</p>

<p>The diagram below illustrates the structure and components of the architecture.</p>

<p>AdHive has developed a distributed multi-layered AI cluster architecture. The development of an AI cluster ensures the performance of recognition functions and guarantees high fault tolerance. The diagram below illustrates the structure and components of the architecture.</p>

<p>Let us look through the components of the AI cluster architecture and briefly examine the functions each block is responsible for.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/architecture.png"/>

<h1>RabbitMQ</h1>

<p>RabbitMQ, otherwise known as a message queue. This unit is responsible for receiving commands for their subsequent execution (recognition, downloading of content, etc.) and storing them until the transfer of received commands to the next service.</p>
 
<h1>COMPFRAMEWORK = Computation Framework</h1>
 
<p>The Compframework module is an analogy of the Computation Framework. To see the concept of the Computation Framework, please follow the given link <a href="http://scorch.ai/Technology/computation-framework/">http://scorch.ai/Technology/computation-framework/</a></p>

<p>The given architecture component is responsible for preparing and downloading data for other recognition levels, controlling the interaction and transmission of data by levels, data integration processes with input-output sources, and data conversion for output formats. In our case, the module integrates with RabbitMQ.</p>

<h1>SMART-SENDER</h1>

<p>This module guarantees the following processes:</p>

<ul>
<li>Delivering tasks to smart from the compframework</li>
<li>Receiving a response from a smart and transferring it to a compframework
<ul>
</ul>
  
<p>Smart Sender interacts with Redis to save an optimized state. In addition, this module uses RabbitMQ as an intermediary between it and the compframework.</p>
<p>The smart module and the compframework module communicate via the http protocol. Compframework is installed together with smart locally on one device according to the following principle - one smart-sender module per smart module.
</p>
 
<h1>SMART</h1>

<p>Smart is the lowest-level block of the described system. It combines various algorithms for recognizing video and audio objects using the neural networks Convolution and Reccucent Neural Networks.</p>
<p>The Smart block consists of the main control program and modules executed as dynamically uploaded libraries.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/smart.png"/>

<h1>Module = algorithm</h1>

<p>Each module has its own configuration file and is equipped with a standard interface. Thus, in order to change the module or make a new one, there is no need to introduce changes to the Smart itself, since the modules to be loaded are added directly to the management file.</p>

<p>Modules in SMART are algorithms for processing video, audio or text data. Each separate module is a dynamically loaded library with a standard interface.</p>

<h2>AI cluster - brief workflow description</h2>

<li><p>From an external service, a command is sent to the MQ message queue to process video or photos.</p></li>
<li><p>Next, the command enters the Computation Framework. The data is downloaded and converted to the required format, and transferred to the next level of processing.</p></li>
<li><p>Ultimately, through the MQ, the command arrives at the SMART module, where images and sounds are recognized.</p></li>
<li><p>If at any stage of the SMART or Computation Framework, the modules did not express readiness to accept the command for processing, the command is transferred to a neighboring server, and this is done by Load Balancing.</p></li>
<li><p>The received responses about the processing courses arrive at the Computation Framework, where they are packaged and sent back to the external service that requested the processing.</p></li>

<h2>How SMART works:</h2>

<p>In the current implementation, the module can recognize photo, video or audio data by various algorithms to select the module configurator.</p>

<p>- Smart contains an asynchronous HTTP server that receives data and sends it to the controller system.</p>
<p>- The controller system receives a command, selects the appropriate handler and starts execution.</p>
<p>- Smart is the central module. It manages the input/output system in turn. Each input/output system is individual for this module.</p>
<p>- After receiving the command for processing, Smart adds the request to the queue of the desired module.</p>
<p>- Next, integration is carried out with the external service, all under the control of Load Balancing. It is then determined which of the SMART modules is ready to process the command at the moment.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/smart1.png"/>

<p>Smart is executed in 3 main streams:</p>
<li><p>Http - server</p></li>
<li><p>Input-output stream</p></li>
<li><p>Smart</p></li>
<p>Plus separate streams for each module.</p>
<p><b>The input/output stream</b> is a set of methods for sending http requests for reading video, audio, text files to be added to the processing queue for a specific module.<p>

<p><b>How is the module selected?</b> The principle of choice is simple as data of the same type is added to all modules that process that particular type of data. For example, audio files are sent to all modules that process audio, etc.</p>

<p><b>Module = Neural network.</b> Thus, the module is a neural network implementation, which has the form of an uploadable library with a standard interface. The structure of the module provides convenience and efficiency in updating the neural network.</p>

<p>The process of adding is as follows - the module is loaded into the necessary directory on the server and its parameters are added to the configuration file. After that, the Smart reboots, and is loaded with the updated neural network.</p>

