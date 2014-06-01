<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MediaManager Skeleton page with VLC Web Plugin</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body align="center">
<h2>The Sorrow and the Pity <%= new java.util.Date() %></h2>

<p>
    <embed type="application/x-vlc-plugin" pluginspage="http://www.videolan.org"
           width="640"
           height="480"
           id="vlc"
           branding="false"
            >
    </embed>
</p>


<p>The Sorrow and the Pity (French: Le chagrin et la pitie) is a two-part 1969 documentary film by Marcel Ophuls about
    the collaboration between the Vichy government and Nazi Germany during World War II. The film uses interviews with a
    German officer, collaborators, and resistance fighters from Clermont-Ferrand. They comment on the nature of and
    reasons for collaboration. The reasons include antisemitism, anglophobia, fear of Bolsheviks and Soviet invasion,
    the desire for power, and simple caution.</p>
<script language="Javascript">
<!--
var vlc = document.getElementById("vlc");
vlc.audio.toggleMute();
var id = vlc.playlist.add("test.avi");
var id1 = vlc.playlist.add("test1.avi");
//vlc.playlist.playItem(id);
//vlc.playlist.next();
//vlc.video.fullscreen = true;
//!-->

</script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>