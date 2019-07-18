<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Login extends REST_Controller {

	
 function login_post(){
	 $username = $this->post('username');
	$password = $this->post('password');

	$get_user = $this->db->query("SELECT * FROM tempat_sewa WHERE username='$username' AND password='$password'")->result();
	if(!empty($get_user)){
		$this->response(array(
			"status"=>"success",
			"result"=> $get_user
		));
	}else{
		$this->response(array(
			"status"=>"failed"
		));
	}

	}
	
 function loginEmail_post(){
	 $email = $this->post('email');
	 $login = $this->db->query("SELECT * FROM tempat_sewa where email= '$email'")->result();
	 if(!empty($login)){
		 $this->response(array(
			 "status" =>"sucess",
			 "result" => $login
		 ));
	 }else{
		 $this->response(array(
			 "status" =>"failed"
		 )
		 );
	 }
 }
}
