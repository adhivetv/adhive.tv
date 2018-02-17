# adhive.tv

<ul>
 <li><a href="#AI cluster - brief workflow description">AI cluster - brief workflow description</a>
 <li><a href="#How SMART works">How SMART works</a>
</ul>

<a name="AI cluster - brief workflow description"></a><h1>AI cluster - brief workflow description</h1>

<ul>
<li><p>From an external service, a command is sent to the MQ message queue to process video or photos.</p>
<li><p>Next, the command enters the Computation Framework. The data is downloaded and converted to the required format, and transferred to the next level of processing.</p>
<li><p>Ultimately, through the MQ, the command arrives at the SMART module, where images and sounds are recognized.</p>
<li><p>If at any stage of the SMART or Computation Framework, the modules did not express readiness to accept the command for processing, the command is transferred to a neighboring server, and this is done by Load Balancing.</p>
<li><p>The received responses about the processing courses arrive at the Computation Framework, where they are packaged and sent back to the external service that requested the processing.</p>
</ul>

<a name="How SMART works"></a><h1>How SMART works:</h1>

<p>In the current implementation, the module can recognize photo, video or audio data by various algorithms to select the module configurator.</p>

<p>- Smart contains an asynchronous HTTP server that receives data and sends it to the controller system.</p>
<p>- The controller system receives a command, selects the appropriate handler and starts execution.</p>
<p>- Smart is the central module. It manages the input/output system in turn. Each input/output system is individual for this module.</p>
<p>- After receiving the command for processing, Smart adds the request to the queue of the desired module.</p>
<p>- Next, integration is carried out with the external service, all under the control of Load Balancing. It is then determined which of the SMART modules is ready to process the command at the moment.</p>

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/smart1.png"/>

<p>Smart is executed in 3 main streams:</p>
<ul>
<li><p>Http - server</p>
<li><p>Input-output stream</p>
<li><p>Smart</p>
</ul>
<p>Plus separate streams for each module.</p>
<p><b>The input/output stream</b> is a set of methods for sending http requests for reading video, audio, text files to be added to the processing queue for a specific module.<p>

<p><b>How is the module selected?</b> The principle of choice is simple as data of the same type is added to all modules that process that particular type of data. For example, audio files are sent to all modules that process audio, etc.</p>

<p><b>Module = Neural network.</b> Thus, the module is a neural network implementation, which has the form of an uploadable library with a standard interface. The structure of the module provides convenience and efficiency in updating the neural network.</p>

<p>The process of adding is as follows - the module is loaded into the necessary directory on the server and its parameters are added to the configuration file. After that, the Smart reboots, and is loaded with the updated neural network.</p>
