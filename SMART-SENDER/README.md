# adhive.tv
<h1>SMART-SENDER</h1>

<ul>
<li><a href="https://github.com/adhivetv/adhive.tv">Background</a></li>
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/RabbitMQ">RabbitMQ</a>
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/COMPFRAMEWORK">COMPFRAMEWORK</a> 
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/SMART-SENDER">SMART-SENDER</a>
<li><a href="https://github.com/adhivetv/adhive.tv/tree/master/ai">AI</a>
<ul>
</ul>
  
<p>This module guarantees the following processes:</p>
<li><p>Delivering tasks to smart from the compframework<p></li>
<li><p>Receiving a response from a smart and transferring it to a compframewor</p></li>

<p>Smart Sender interacts with Redis to save an optimized state. In addition, this module uses RabbitMQ as an intermediary between it and the compframework.</p>
<p>The smart module and the compframework module communicate via the http protocol. Compframework is installed together with smart locally on one device according to the following principle - one smart-sender module per smart module.</p>

