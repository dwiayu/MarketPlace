<?php
defined('BASEPATH') OR exit('No direct script access allowed');
// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';
class Login extends REST_Controller{

	function login_post(){
	$username = $this->post('username');
	$password = $this->post('password');

	$get_user = $this->db->query("SELECT * FROM user WHERE username='$username' AND password='$password'");
	if($get_user->row()->level=="Penyewa"){
		$this->response(
			array(
				"status"=>"Penyewa",
				"result" =>$get_user->row()->id_user
			)
			);

	}
	else {
		$this->response(
			array(
				"status"=>"gagal",
				"result" =>null
			)
			);
	}
 }



}