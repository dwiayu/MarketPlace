<?php
defined('BASEPATH') OR exit('No direct script access allowed');
// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';
class tempatSewa extends REST_Controller{

	public function tempatSewa_get(){
		$id_tempat = $this->get('id_tempat');
		if ($id_tempat == '') {

			$tempat = $this->db->query("SELECT * FROM tempat_sewa WHERE izin='ya'")->result();
		}else{
			$this->db->where('id_tempat',$id_tempat);
			$tempat = $this->db->query("SELECT * FROM tempat_sewa WHERE izin='ya' AND id_tempat='$id_tempat'")->result();
		}
		$this->response($tempat,200);
	} 



}