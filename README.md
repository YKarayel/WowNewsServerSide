# WowNewsServerSide
<b>It is the server side of the android application called WoW News.</b>

<p>App Link: https://play.google.com/store/apps/details?id=com.nexuswawe.wownews</p>
<p>For client side codes : https://github.com/YKarayel/WowNewsClientApp</p>

<h2>What the server does:</h2>
<ul>

<li>With the Webscrapping (html) method , it pulls the datas from the other side.</li>
<li>It makes the data available for use.</li>
<li>Data is sent to Firebase Realtime Database for Client to use.</li>
<li>Communicates with Firebase Messaging and it sets the topics</li>
<li>It check if there is a new post.</li>
<li>If there is a new post, it sends push-notification to the client side via Firebase Messaging.</li>
</ul>

<h3> Is this server available at the moment ?</h3>
  <li> Yes, it's always running on a remote Linux server </li>

  <h3>How does the server work?</h3>

  <li>When the server completes its operations, it is held in standby mode for 15 minutes by the ScheduledExecutorService.</li>
  <li>After 15 minutes the server wakes up again and this cycle continues until it is stopped by the developer</li>

  <h3>How did you do the instance management?</h3>
  <li>I did not use any IOC containers. Because I work mainly with .Net, it would take time to create and learn a new container.</li>
  <li>I used AutoClosable to control the instances. the instance is set to be closed each time a new instance is created and used and the process ends</li>


  https://github.com/YKarayel/WowNewsServerSide/assets/121500914/b77ec5a1-f54b-4856-9edc-cb6354740235
