<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Pendaftaran extends REST_Controller {
	private $folder_upload='uploads/';


	function all_post(){
		$level = 'Penyewa';
		$Photo = 'photo.png';
		$action = $this->post('action');
		$data_pendaftaran = array(
			'id_user' =>$this->post('id_user'),
			'nama' => $this->post('nama'),
			'jenis_kelamin' => $this->post('jenis_kelamin'),
			'email' => $this->post('email'),
			'no_hp' =>$this->post('no_hp'),
			'foto_user'=>($Photo),
			'username' =>$this->post('username'),
			'password' =>$this->post('password'),
			'level' => ($level)
		);
		switch ($action){
			case 'insert';
			$this->insertPendaftaran($data_pendaftaran);
			break;

			default: 
			$this->response (
				array(
					"status" =>"failed", 
					"message" =>"action harus diisi"
				)
				);
				break;
		}
	}
	function insertPendaftaran($data_pendaftaran){
		
				// Cek validasi
				if (empty($data_pendaftaran['nama'])||empty($data_pendaftaran['jenis_kelamin'])||empty($data_pendaftaran['email']) || empty($data_pendaftaran['no_hp']) ||
				empty($data_pendaftaran['username']) || empty($data_pendaftaran['password'])){
					$this->response(
						array(
							"status" => "failed",
							"message" => "Lengkapi Data"
						)
					);
				} 
			else{
				$getUsername =$this->db->query("SELECT username from user where username='".$data_pendaftaran['username']."'")->result();
				$message="";
				if(!empty($getUsername)) $message.="username sudah tersedia";
				if(empty($message)){
					
								$data_pendaftaran['foto_user'] = $this->uploadPhoto();
					
								$do_insert = $this->db->insert('user', $data_pendaftaran);
							   
								if ($do_insert){
									$this->response(
										array(
											"status" => "success",
											"result" => array($data_pendaftaran),
											"message" => $do_insert
										)
									);
								}
							}
			}
		
	}

}
