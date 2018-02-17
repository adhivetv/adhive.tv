# adhive.tv

<ul>
 <li><a href="#Distributed Multi-Layered AI Cluster Architecture">Distributed Multi-Layered AI Cluster Architecture</a>
 <li><a href="#RabbitMQ">RabbitMQ</a>
 <li><a href="#COMPFRAMEWORK">COMPFRAMEWORK</a> 
 <li><a href="#SMART-SENDER">SMART-SENDER</a>
 <li><a href="#SMART">SMART</a>
 <li><a href="#Module">Module</a>
</ul>
 
<a name="Distributed Multi-Layered AI Cluster Architecture"></a><h1>Distributed Multi-Layered AI Cluster Architecture</h1>
 
<p>The market has been plagued by various issues related to correct identification and placement of content. The main problem at the moment is video and photo file recognition from various internet sources without losing data components.</p>
 
<p>The solution in this regard is to ensure the performance and fault tolerance of recognition functions. To solve the issue and bring high quality content to the market, AdHive has developed a distributed multi-layered AI cluster architecture. The development of the AI cluster ensures the performance of the recognition functions and guarantees its fault tolerance.</p>

<p>The diagram below illustrates the structure and components of the architecture.</p>

<p>AdHive has developed a distributed multi-layered AI cluster architecture. The development of an AI cluster ensures the performance of recognition functions and guarantees high fault tolerance. The diagram below illustrates the structure and components of the architecture.</p>

<p>Let us look through the components of the AI cluster architecture and briefly examine the functions each block is responsible for.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/architecture.png"/>

<a name="RabbitMQ"></a><h1>RabbitMQ</h1>

<p>RabbitMQ, otherwise known as a message queue. This unit is responsible for receiving commands for their subsequent execution (recognition, downloading of content, etc.) and storing them until the transfer of received commands to the next service.</p>
 
<a name="COMPFRAMEWORK"></a><h1>COMPFRAMEWORK = Computation Framework</h1>
 
<p>The Compframework module is an analogy of the Computation Framework. To see the concept of the Computation Framework, please follow the given link <a href="http://scorch.ai/Technology/computation-framework/">http://scorch.ai/Technology/computation-framework/</a></p>

<p>The given architecture component is responsible for preparing and downloading data for other recognition levels, controlling the interaction and transmission of data by levels, data integration processes with input-output sources, and data conversion for output formats. In our case, the module integrates with RabbitMQ.</p>

<a name="SMART-SENDER"></a><h1>SMART-SENDER</h1>

<p>This module guarantees the following processes:</p>

<ul>
<li>Delivering tasks to smart from the compframework
<li>Receiving a response from a smart and transferring it to a compframework
</ul>
  
<p>Smart Sender interacts with Redis to save an optimized state. In addition, this module uses RabbitMQ as an intermediary between it and the compframework.</p>
<p>The smart module and the compframework module communicate via the http protocol. Compframework is installed together with smart locally on one device according to the following principle - one smart-sender module per smart module.
</p>
 
<a name="SMART"></a><h1>SMART</h1>

<p>Smart is the lowest-level block of the described system. It combines various algorithms for recognizing video and audio objects using the neural networks Convolution and Reccucent Neural Networks.</p>
<p>The Smart block consists of the main control program and modules executed as dynamically uploaded libraries.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/smart.png"/>

<a name="Module"></a><h1>Module = algorithm</h1>

<p>Each module has its own configuration file and is equipped with a standard interface. Thus, in order to change the module or make a new one, there is no need to introduce changes to the Smart itself, since the modules to be loaded are added directly to the management file.</p>

<p>Modules in SMART are algorithms for processing video, audio or text data. Each separate module is a dynamically loaded library with a standard interface.</p>
