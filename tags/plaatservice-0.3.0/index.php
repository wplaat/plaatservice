<?php

/* 
**  ============
**  PlaatService
**  ============
**
**  Created by wplaat
**
**  For more information visit the following website.
**  Website : www.plaatsoft.nl 
**
**  Or send an email to the following address.
**  Email   : info@plaatsoft.nl
**
**  All copyrights reserved (c) 2008-2016 PlaatSoft
*/

/* ----------------------------------------------------- */
/* Store HTML Request in database */
/* ----------------------------------------------------- */

$json["Java-RedSquare"]='0.3';
$json["Java-KnightsQuest"]='0.4';
	
$json["PlaatEnergy"]='1.6';
$json["PlaatScrum"]='1.3';
$json["PlaatProtect"]='0.6';
$json["PlaatSign"]='1.1';
$json["PlaatService"]='0.2';
$json["PlaatDishes"]='0.1';
	
$json["Windows-ChatCostCalc"]='0.50';
$json["Windows-WarQuest"]='1.6';
$json["Windows-RedSquare"]='1.0';
$json["Windows-PlaatStats"]='1.1';
$json["Windows-PlaatScore"]='0.70';
	
$json["Android-WarQuest"]='1.0';
$json["Android-RedSquare"]='0.1';
	
$json["Symbian-WarQuest"]='1.0';

$json["Linux-WarQuest"]='1.0';
$json["Linux-RedSquare"]='1.0';
	
$json["Wii-BibleQuiz"]='0.95';
$json["Wii-KnightsQuest"]='0.1';
$json["Wii-Pong2"]='1.0';
$json["Wii-RedSquare"]='1.0';
$json["Wii-SpaceBubble"]='0.98';
$json["Wii-TowerDefense"]='0.98';
	
$json["Drupal-Address"]='4.2';
$json["Drupal-EventNotification"]='2.2';
$json["Drupal-ChurchAdmin"]='1.1';
	
/* ----------------------------------------------------- */
/* Utils */
/* ----------------------------------------------------- */

function plaatservice_post($label, $default) {
	
	$value = $default;
	
	if (isset($_POST[$label])) {
		$value = $_POST[$label];
		$value = stripslashes($value);
		$value = htmlspecialchars($value);
	}
		
	return $value;
}

include "config.php";
include "database.php";

plaatservice_db_connect($config["dbhost"], $config["dbuser"], $config["dbpass"], $config["dbname"]);
plaatservice_db_check_version();

$action = plaatservice_post("action", "");
$product_name = plaatservice_post("product", "");
$product_version = plaatservice_post("version", "");
$product_os = plaatservice_post("os", "");
$dt = plaatservice_post("dt", 0);
$score = plaatservice_post("score", 0);
$level = plaatservice_post("level", 0);
$username = plaatservice_post("username", "");
$nickname = plaatservice_post("nickname", "");
$country = plaatservice_post("country", "");
$city = plaatservice_post("city", "");
$pid = plaatservice_post("pid", 0);
$uid = plaatservice_post("uid", 0);

$ip="0.0.0.0";
if (isset($_SERVER["REMOTE_ADDR"])) {
	$ip = $_SERVER["REMOTE_ADDR"];
}

/* ----------------------------------------------------- */
/* State Machine */
/* ----------------------------------------------------- */

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Cache-Control: no-cache");
header("Pragma: no-cache");

if ($action == "getVersion" ) {

	$version = @$json[$product_name];
	
	if (strlen($version)==0) {
		$version="0.0";
	}
	
	$data = array();
	$data["product"]=$version;
			
	echo json_encode($data);

} else if ($action == "getUser" ) {

	$user = plaatservice_db_user_find($username, $ip);
	
	if (!isset($user->uid)) {
	
		/* If user not exist, create it */
		plaatservice_db_user_insert($username, $ip, $nickname, $country, $city);		
		$user = plaatservice_db_user_find($username, $ip);
	} 
	echo json_encode($user);
	
} else if ($action == "setUser" ) {

	$user = plaatservice_db_user_find($username, $ip);
	
	if (isset($user->uid)) {
	
		/* Update nickname */
		if ((strlen($nickname)>0) && ($nickname<>$user->nickname)) {
			$user->nickname = $nickname;	
			$change = true;
		}
		plaatservice_db_user_update($user);
	} 	
	echo json_encode($user);

} else if ($action == "getProduct" ) {

	$product = plaatservice_db_product_find($product_name, $product_version, $product_os);
	
	if (!isset($product->pid)) {
	
		/* If product not exist, create it */
		if ((strlen($product_name)>0) && (strlen($product_version)>0)) {
			plaatservice_db_product_insert($product_name, $product_version, $product_os);
			$product = plaatservice_db_product_find($product_name, $product_version, $product_os);		
		}
	} 
	
	echo json_encode($product);
	
} else if ($action == "setScore" ) {

	$product = plaatservice_db_product($pid);	
	$user = plaatservice_db_user($uid);
	
	if (isset($user->uid) && isset($product->pid) && ($dt!=0) && ($score!=0) && ($level!=0)) {
		plaatservice_db_score_insert($product->pid, $user->uid, $dt, $score, $level);
	}
	
} else if ($action == "getLocalScore" ) {

	$product = plaatservice_db_product($pid);	
	$user = plaatservice_db_user($uid);
	
	if (isset($user->uid) && isset($product->pid)) {
	
		$result = plaatservice_db_score_local($product->pid, $user->uid);
		
		$store = array();
		$count=0;
		while ($data=plaatservice_db_fetch_object($result)) {			
			$store[""+$count++]=$data;
		}
		echo json_encode($store);
	}
	
} else if ($action == "getGlobalScore" ) {

	$product = plaatservice_db_product($pid);	
	if (isset($product->pid)) {
	
		$data = plaatservice_db_score_global($product->pid);
		echo json_encode($data);
	}
	
} else {

	$data = plaatservice_db_registration($product_name, $product_version, $ip);
	
	if (isset($data->rid)) {
		$data->count++;
		$data->last_date=date("Y-m-d H:i:s");
		plaatservice_db_registration_update($data);
		
	} else {
	
		plaatservice_db_registration_insert($product_name, $product_version, $ip);
	}
	echo json_encode($json);
}

/* ----------------------------------------------------- */
/* The end */
/* ----------------------------------------------------- */

?>
