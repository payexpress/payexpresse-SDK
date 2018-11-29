<?php
require_once 'conf.php';
?>

<!doctype html>
<html lang="en">

<!-- Mirrored from demo.angelostudio.net/1e-shop/1.3/ by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 24 Mar 2018 10:14:44 GMT -->
<!-- Added by HTTrack --><meta http-equiv="content-type" content="text/html;charset=UTF-8" /><!-- /Added by HTTrack -->
<head>
    <title>Boutique PayExpresse</title>
    <meta charset="utf-8">
    <meta name="author" content="PayExpresse">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/turquoise.css" class="colors">
	<link rel="shortcut icon" href="img/ico/32.png" sizes="32x32" type="image/png">
	<link rel="apple-touch-icon" href="img/ico/60.png" type="image/png">
	<link rel="apple-touch-icon" sizes="72x72" href="img/ico/72.png" type="image/png">
	<link rel="apple-touch-icon" sizes="120x120" href="img/ico/120.png" type="image/png">
	<link rel="apple-touch-icon" sizes="152x152" href="img/ico/152.png" type="image/png">
    <link rel="stylesheet" href="https://cdn.payexpresse.com/v1/payexpresse.min.css">

    <meta property="og:site_name" content="PayExpresse">
    <meta property="og:title" content="PayExpresse">
    <meta property="og:description"
          content="PayExpresse | Boutique Démo">
    <meta property="og:image" content="https://sample.payexpresse.com/img/snapshoot.png">
    <meta property="og:url" content="https://sample.payexpresse.com/">
    <meta property="og:type" content="website">
    <meta property="og:locale" content="fr_FR">
    <meta name="generator"
          content="PayExpresse | Boutique Démo"/>
    <script src="https://cdn.payexpresse.com/v1/payexpresse.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://www.googletagmanager.com/gtag/js?id=UA-30125205-11"></script>
    <script>
        if(location.hostname !== "localhost" || location.hostname !== "127.0.0.1"){
            $.getScript( "https://sample.payexpresse.com/js/analytic.js", function( data, textStatus, jqxhr ) {
                console.log( "Load analytics 2 was performed." );
            });
        }
    </script>
    <style>
        .hidden{
            display: none !important;
        }
    </style>

    <script>
        function buy(btn) {
            $('.close-modal').click();

            setTimeout(function () {
                var selector = pQuery(btn);

                (new PayExpresse({
                    item_id          :   1,
                })).withOption({
                    requestTokenUrl           :   '<?= BASE_URL ?>/paiement.php',
                    method              :   'POST',
                    headers             :   {
                        "Accept"          :    "text/html"
                    },
                    prensentationMode   :   PayExpresse.OPEN_IN_POPUP,
                    didPopupClosed: function (is_completed, success_url, cancel_url) {
                        window.location.href = is_completed === true ? success_url  : cancel_url;
                    },
                    willGetToken        :   function () {
                        console.log("Je me prepare a obtenir un token");
                        selector.prop('disabled', true);
                    },
                    didGetToken         : function (token, redirectUrl) {
                        console.log("Mon token est : " +  token  + ' et url est ' + redirectUrl );
                        selector.prop('disabled', false);
                    },
                    didReceiveError: function (error) {
                        alert('erreur inconnu', error.toString());
                        selector.prop('disabled', false);
                    },
                    didReceiveNonSuccessResponse: function (jsonResponse) {
                        console.log('non success response ',jsonResponse);
                        alert(jsonResponse.errors);
                        selector.prop('disabled', false);
                    }
                }).send({
                    pageBackgroundRadianStart:'#0178bc',
                    pageBackgroundRadianEnd:'#00bdda',
                    pageTextPrimaryColor:'#333',
                    paymentFormBackground:'#fff',
                    navControlNextBackgroundRadianStart:'#608d93',
                    navControlNextBackgroundRadianEnd:'#28314e',
                    navControlCancelBackgroundRadianStar:'#28314e',
                    navControlCancelBackgroundRadianEnd:'#608d93',
                    navControlTextColor:'#fff',
                    paymentListItemTextColor:'#555',
                    paymentListItemSelectedBackground:'#eee',
                    commingIconBackgroundRadianStart:'#0178bc',
                    commingIconBackgroundRadianEnd:'#00bdda',
                    commingIconTextColor:'#fff',
                    formInputBackgroundColor:'#eff1f2',
                    formInputBorderTopColor:'#e3e7eb',
                    formInputBorderLeftColor:'#7c7c7c',
                    totalIconBackgroundRadianStart:'#0178bc',
                    totalIconBackgroundRadianEnd:'#00bdda',
                    formLabelTextColor:'#292b2c',
                    alertDialogTextColor:'#333',
                    alertDialogConfirmButtonBackgroundColor:'#0178bc',
                    alertDialogConfirmButtonTextColor:'#fff'
                });
            }, 500)

        }
    </script>
</head>

<body id="home">
	<a id="menu-link" href="#" class="hidden">
		<span class="menu-icon"></span>
	</a>
      
	<div class="overlay" id="overlay hidden">
        <nav class="overlay-menu">
          <ul>
            <li><a href="#home" class="smooth-scroll">Home</a></li>
            <li><a href="#start" class="smooth-scroll">About product</a></li>
            <li><a href="#showcase" class="smooth-scroll">Showcase</a></li>
            <li><a href="#requirements" class="smooth-scroll">Requirements</a></li>
            <li><a href="#features" class="smooth-scroll">Features</a></li>
            <li><a href="#contact" class="smooth-scroll">Contact</a></li>
            <li><a href="https://www.google.com/" class="smooth-scroll">External link</a></li>
          </ul>
        </nav>
      </div>
	
	<div id="wrap">
		<section id="hero" class="m-center text-center bg-shop full-height">
			<div class="center-box">
				
					<div class="hero-unit ">
						<div class="container ">
						<h1 class="title"><a href="https://payexpresse.com/" target="_blank" style="color: #fff;"><b>PayExpresse </b></a> Boutique Test</h1>
						<h3><a href="https://payexpresse.com/" target="_blank" style="color: #fff;"><b>PayExpresse </b></a> la meilleure plateforme de paiement en ligne Web & Mobile</h3>
						<p><br>
                            Si vous souhaitez vendre des produits, à partir de votre site,<br>
						<a href="https://payexpresse.com/" target="_blank" style="color: #fff;"><b>PayExpresse</b></a> est la solution.<br>
						</p>
						<br>
						<a class="btn white" href="#" data-toggle="modal" data-target="#product-modal"><b>55 000 FCFA</b> Acheter</a>
						</div>
					</div>
					<div class="col-sm-12 img-hero"></div>
				
			</div>
		</section>
        <section id="features" class="features-1">
            <div class="container padding-top-bottom">
                <div class="row header">
                    <div class="col-md-12">
                        <h2>Partenaires</h2>
                        <p>Ils nous ont fait confiance</p>
                    </div>
                </div>
                <div class="container" >
                    <div class="row">
                        <div class="col-md-4 anima scale-in ">
                            <article class="text-center">
                                <a href="https://empiredigital.info/" target="_blank"><img src="img/empire.png" alt="#" class="zoom-img img-fluid center-block" style="height: 180px;object-fit: cover;"></a>
                                <h3>​Empire Digital</h3>
                                <p>Créez vos campagnes SMS et Emailling en un clic.Rapide, Facile et 100% efficace. Solutions Informatiques.Web Marketing.</p>
                            </article>
                        </div>
                        <div class="col-md-4 anima scale-in d1">
                            <article class="text-center">
                                <a href="https://www.expat-dakar.com/" target="_blank"><img src="img/expat.png" alt="#" class="zoom-img img-fluid center-block" style="height: 180px;object-fit: cover;"></a>
                                <h3>​Expat Dakar</h3>
                                <p>Tout acheter, tout vendre sur le plus grand site de petites annonces au Sénégal. Trouvez la bonne affaire maintenant !</p>
                            </article>
                        </div>
                        <div class="col-md-4 anima scale-in d2">
                            <article class="text-center">
                                <a href="https://www.sendeal.sn/" target="_blank"><img src="img/sen.png" alt="#" class="zoom-img img-fluid center-block" style="height: 180px;object-fit: cover;"></a>
                                <h3>SenDeal</h3>
                                <p>Sendeal site de vente n°1 de l'achat groupé en Afrique.</p>
                            </article>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div class="footer-1 text-center">
            <div class="container-fluid">
                <a href="#home" class="back-to-top smooth-scroll"><i class="fa fa-chevron-up"></i></a>
                <p> <i class="fa fa-heart color-text"></i> Par <a href="https://payexpresse.com/" target="_blank">PayExpresse</a>.</p>
            	<ul class="social-links-2 ">
					<li><a href="https://www.linkedin.com/company/empiredigital.info" target="_blank"><i class="fa fa-linkedin"></i></a></li>
					<li><a href="https://twitter.com/pexpresse" target="_blank"><i class="fa fa-twitter"></i></a></li>
				</ul>
            </div>
        </div>

		<div class="modal fade" id="product-modal" tabindex="-1" role="dialog" aria-hidden="true">
		    <div class="modal-dialog modal-lg">
		        <div class="modal-content">
		            <div class="modal-header">
		                <a class="close-modal" href="#" data-dismiss="modal">
		                    <span class="menu-icon"></span>
		                </a>
		                <img src="img/cup1.jpg" alt="" class="img-fluid">
		            </div>
		            <div class="modal-body">
		                <h3 class="text-center"><b>PaperCup - Mockup </b>(55 000 FCFA)</h3>
		            </div>
		            <div class="modal-footer">
		            	<div class="container">
			                <form id="buy" action="" class="myform" method="post" novalidate>
			                    <div class="form-group">
			                        <button onclick="buy()" type="button" class="btn btn-block">Confirmer</button>
			                    </div>
			                    <p class="text-center"><a href="#" data-dismiss="modal">Annuler</a>
			                    </p>
			                </form>
		            </div>
		            </div>
		        </div>
		    </div>
		</div>


		<section id="style-switcher" class="hidden" >
            <h2>Colors <a href="#"><i class="fa fa-tint"></i></a></h2>
            <ul>
                <li id="yellow"></li>
                <li id="purple"></li>
                <li id="turquoise"></li>
                <li id="blue"></li>
                <li id="red"></li>
                <li id="brown"></li>
            </ul>
        </section>

	</div>
    <!-- Core scrips -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/2.0.3/waypoints.min.js"></script>
    <script type="text/javascript" src="js/core.js"></script>
    <script type="text/javascript" src="js/menu-overlay.js"></script>
    <script type="text/javascript" src="js/placeholders.min.js"></script>
    <!-- end core scripts --> 
    <!-- sliders -->
    <script type="text/javascript" src="js/owl.carousel.min.js"></script>   	
   	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.waitforimages/2.4.0/jquery.waitforimages.min.js"></script> 
   	<script type="text/javascript" src="js/sliders.js"></script>
	<script type="text/javascript" src="js/jquery.counterup.min.js"></script>
	<script type="text/javascript" src="js/numbers.js"></script>
	<script type="text/javascript" src="js/contact.js"></script>
	<script type="text/javascript" src="js/parallax.js"></script>
	<script type="text/javascript" src="js/ga.js"></script>

</body>


</html>
