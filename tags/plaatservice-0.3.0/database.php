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

/* 
** -----------------
** GENERAL
** ----------------- 
*/

/**
 * connect to database
 * @param $dbhost database hostname
 * @param $dbuser database username
 * @param $dbpass database password
 * @param $dbname database name
 * @return connect result (true = successfull connected | false = connection failed)
 */
function plaatservice_db_connect($dbhost, $dbuser, $dbpass, $dbname) {

	global $db;

   $db = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);	
	if (mysqli_connect_errno()) {
		plaatservice_db_error();
		return false;		
	}
	return true;
}

/**
 * Disconnect from database  
 * @return disconnect result
 */
function plaatservice_db_close() {

	global $db;

	mysqli_close($db);

	return true;
}

/**
 * Show SQL error 
 * @return HTML formatted SQL error
 */
function plaatservice_db_error() {

	if (DEBUG == 1) {
		echo mysqli_connect_error(). "<br/>\n\r";
	}
}

/**
 * Count queries 
 * @return queries count
 */
$query_count=0;
function plaatservice_db_count() {

	global $query_count;
	return $query_count;
}

/**
 * Execute database multi query
 */
function plaatservice_db_multi_query($queries) {

	$tokens = @preg_split("/;/", $queries);
	foreach ($tokens as $token) {
	
		$token=trim($token);
		if (strlen($token)>3) {
			plaatservice_db_query($token);		
		}
	}
}

/**
 * Execute database query
 * @param $query SQL query with will be executed.
 * @return Database result
 */
function plaatservice_db_query($query) {
			
	global $query_count;
	global $db;
	
	$query_count++;

	if (DEBUG == 1) {
		echo $query."<br/>\r\n";
	}

	@$result = mysqli_query($db, $query);

	if (!$result) {
		plaatservice_db_error();		
	}
	
	return $result;
}

/**
 * escap database string
 * @param $data  input.
 * @return $data escaped
 */
function plaatservice_db_escape($data) {

	global $db;
	
	return mysqli_real_escape_string($db, $data);
}

/**
 * Fetch query result 
 * @return mysql data set if any
 */
function plaatservice_db_fetch_object($result) {
	
	$row="";
	
	if (isset($result)) {
		$row = $result->fetch_object();
	}
	return $row;
}

/**
 * Return number of rows
 * @return number of row in dataset
 */
function plaatservice_db_num_rows($result) {
	
	return mysqli_num_rows($result);
}

/*
** ------------------------
** CREATED / PATCH DATABASE
** ------------------------
*/

function startsWith($haystack, $needle){
    $length = strlen($needle);
    return (substr($haystack, 0, $length) === $needle);
}

/**
 * Execute SQL script
 * @param $version Version of sql patch file
 */
function plaatservice_db_execute_sql_file($version) {

    $filename = 'database/patch-'.$version.'.sql';

    $commands = file_get_contents($filename);

    //delete comments
    $lines = explode("\n",$commands);
    $commands = '';
    foreach($lines as $line){
        $line = trim($line);
        if( $line && !startsWith($line,'--') ){
            $commands .= $line . "\n";
        }
    }

    //convert to array
    $commands = explode(";\n", $commands);

    //run commands
    $total = $success = 0;
    foreach($commands as $command){
        if(trim($command)){
	         $success += (@plaatservice_db_query($command)==false ? 0 : 1);
            $total += 1;
        }
    }

    //return number of successful queries and total number of queries found
    return array(
        "success" => $success,
        "total" => $total
    );
}

/**
 * Check db version and upgrade if needed!
 */
function plaatservice_db_check_version() {

   // Create database if needed	
   $sql = "select 1 FROM registration limit 1" ;
   $result = plaatservice_db_query($sql);
   if (!$result) {
		$version="0.1";
      plaatservice_db_execute_sql_file($version);
   }
	
	$sql = "select 1 FROM score limit 1" ;
   $result = plaatservice_db_query($sql);
   if (!$result) {
		$version="0.2";
      plaatservice_db_execute_sql_file($version);
   }
	
	$sql = "select 1 FROM user limit 1" ;
   $result = plaatservice_db_query($sql);
   if (!$result) {
		$version="0.3";
      plaatservice_db_execute_sql_file($version);
   }
}

/*
** -----------------
** REGISTRATION
** -----------------
*/

function plaatservice_db_registration_insert($product, $version, $ip) {

	$query  = 'insert into registration (product, version, ip, count, create_date, last_update) ';
	$query .= 'values ("'.plaatservice_db_escape($product).'","'.plaatservice_db_escape($version).'",';
	$query .= '"'.plaatservice_db_escape($ip).'",1,"'.date("Y-m-d H:i:s").'","'.date("Y-m-d H:i:s").'")';
	plaatservice_db_query($query);
}

function plaatservice_db_registration_update($data) {
		
	$query  = 'update registration set '; 
	$query .= 'product="'.plaatservice_db_escape($data->product).'", ';
	$query .= 'version="'.plaatservice_db_escape($data->version).'", ';
	$query .= 'ip="'.plaatservice_db_escape($data->ip).'", ';
	$query .= 'count='.$data->count.', ';
	$query .= 'create_date="'.$data->create_date.'", ';
	$query .= 'last_update="'.$data->last_date.'" ';
	$query .= 'where rid='.$data->rid; 
	
	plaatservice_db_query($query);
}

function plaatservice_db_registrtion_remove($rid) {
		
	$query  = 'delete from registration where uid='.$rid;	
	plaatservice_db_query($query);  
}

function plaatservice_db_registration($product, $version, $ip) {
	
	$query  = 'select rid, product, version, ip, count, create_date, last_update ';
	$query .= 'from registration where product="'.plaatservice_db_escape($product).'" and ';
	$query .= 'version="'.plaatservice_db_escape($version).'" and ip="'.plaatservice_db_escape($ip).'"';	
	
	$result = plaatservice_db_query($query);
	$data = plaatservice_db_fetch_object($result);
	
	return $data;
}

/*
** -----------------
** SCORE
** -----------------
*/

function plaatservice_db_score_insert($pid, $uid, $dt, $score, $level) {

	$query  = 'insert into score (pid, uid, dt, score, level) ';
	$query .= 'values (';
	$query .= $pid.',';
	$query .= $uid.',';
	$query .= '"'.date('Y-m-d H:i:s',$dt).'",';
	$query .= $score.',';
	$query .= $level.')';
	
   plaatservice_db_query($query);
}

function plaatservice_db_score_local($pid, $uid) {
	
	$query  = 'select dt, score, level ';
	$query .= 'from score where ';
	$query .=' pid in ('.plaatservice_db_product_all($pid).') and uid='.$uid.' ';	
	$query .= 'order by score desc limit 0,15';	
	
	$result = plaatservice_db_query($query);	
	return $result;
}

function plaatservice_db_score_global($pid) {
	
	$query1  = 'select uid, max(score) as score from score where pid in (';
	$query1 .= plaatservice_db_product_all($pid).') group by uid order by score desc limit 0,15';
	$result1 = plaatservice_db_query($query1);
	
	$count = 0;
	$data3 = array();
	while ($data1 = plaatservice_db_fetch_object($result1)) {			
		
		$query2  = 'select a.dt, a.score, a.level, b.username, b.nickname, b.country ';
		$query2 .= 'from score a left join user b on a.uid=b.uid where a.uid='.$data1->uid.' and a.score='.$data1->score.' limit 0,1';
		
		$result2 = plaatservice_db_query($query2);
		$data2 = plaatservice_db_fetch_object($result2);
		
		$data3[""+$count++] = $data2;
	}
	
	return $data3;
}

/*
** ---------------------
** USER
** ---------------------
*/

function plaatservice_db_user_find($username, $ip) {
	
	$query  = 'select uid, username, nickname, ip, country, city, create_date, last_update ';
	$query .= 'from user where ';
	$query .= 'username="'.plaatservice_db_escape($username).'" and ';
	$query .= 'ip="'.plaatservice_db_escape($ip).'"';
	
	$result = plaatservice_db_query($query);
	$data = plaatservice_db_fetch_object($result);
	
	return $data;
}

function plaatservice_db_user_insert($username, $ip, $nickname, $country, $city) {

	$query  = 'insert into user (username, nickname, ip, country, city, create_date, last_update) ';
	$query .= 'values (';
	$query .= '"'.plaatservice_db_escape($username).'",';
	$query .= '"'.plaatservice_db_escape($nickname).'",';
	$query .= '"'.plaatservice_db_escape($ip).'",';
	$query .= '"'.plaatservice_db_escape($country).'",';
	$query .= '"'.plaatservice_db_escape($city).'",';
	$query .= '"'.date('Y-m-d H:i:s').'",';
	$query .= '"'.date('Y-m-d H:i:s').'"';
	$query .= ')';
	
   plaatservice_db_query($query);
}

function plaatservice_db_user_update($data) {
		
	$query  = 'update user set '; 
	$query .= 'username="'.plaatservice_db_escape($data->username).'", ';
	$query .= 'ip="'.plaatservice_db_escape($data->ip).'", ';
	$query .= 'nickname="'.plaatservice_db_escape($data->nickname).'", ';	
	$query .= 'country="'.$data->country.'", ';
	$query .= 'city="'.$data->city.'", ';
	$query .= 'last_update="'.date('Y-m-d H:i:s').'" ';
	$query .= 'where uid='.$data->uid; 
	
	plaatservice_db_query($query);
}

function plaatservice_db_user($uid) {
	
	$query  = 'select uid, username, nickname, ip, country, city, create_date ';
	$query .= 'from user where uid='.$uid;
	
	$result = plaatservice_db_query($query);
	$data = plaatservice_db_fetch_object($result);
	
	return $data;
}

/*
** ---------------------
** PRODUCT
** ---------------------
*/

function plaatservice_db_product_all($pid) {
	
	$data = plaatservice_db_product($pid);
	
	$query  = 'select pid from product where name="'.plaatservice_db_escape($data->name).'"';
	$result = plaatservice_db_query($query);
	
	$ids = "";
	while ($data = plaatservice_db_fetch_object($result)) {			
		if (strlen($ids)>0) {
			$ids .= ",";
		}
		$ids .= $data->pid;
	}	

	return $ids;
}


function plaatservice_db_product_find($name, $version, $os) {
	
	$query  = 'select pid, name, version, version ';
	$query .= 'from product where ';
	$query .= 'name="'.plaatservice_db_escape($name).'" and ';
	$query .= 'version="'.plaatservice_db_escape($version).'" and ';
	$query .= 'os="'.plaatservice_db_escape($os).'"';
	
	$result = plaatservice_db_query($query);
	$data = plaatservice_db_fetch_object($result);
	
	return $data;
}

function plaatservice_db_product_insert($name, $version, $os) {

	$query  = 'insert into product (name, version, os, create_date) ';
	$query .= 'values (';
	$query .= '"'.plaatservice_db_escape($name).'",';
	$query .= '"'.plaatservice_db_escape($version).'",';
	$query .= '"'.plaatservice_db_escape($os).'",';
	$query .= '"'.date('Y-m-d H:i:s').'"';
	$query .= ')';
	
   plaatservice_db_query($query);
}

function plaatservice_db_product($pid) {
	
	$query  = 'select pid, name, version, os, create_date ';
	$query .= 'from product where pid='.$pid;
	
	$result = plaatservice_db_query($query);
	$data = plaatservice_db_fetch_object($result);
	
	return $data;
}

/*
** ---------------------
** THE END
** ---------------------
*/

?>