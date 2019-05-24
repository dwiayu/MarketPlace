<?php
defined('BASEPATH') OR exit('No direct script access allowed');
// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';
class Komentar extends REST_Controller{

	public function komentar_get(){
		$id_komentar= $this->get('id_komentar');
		$id_tempat =$this->get('id_tempat');
		if ($id_komentar== '') {
			$komentar= $this->db->get('komentar')->result();
		}else{
			$this->db->where('id_tempat',$id_tempat);
			$this->db->where('id_komentar',$id_komentar);
			$komentar= $this->db->get('komentar')->result();
		}
		$this->response($komentar,200);
	} 

	public function komentarYa_get(){
		$id_tempat = $this->get('id_tempat');
		$komentar = $this->db->query("SELECT count(id_komentar) as jml_ya FROM komentar JOIN sewa ON sewa.id_sewa=komentar.id_sewa JOIN tempat_sewa ON sewa.id_tempat=tempat_sewa.id_tempat WHERE suka='ya' AND sewa.id_tempat='$id_tempat'")->result();
		$this->response($komentar,200);
	
	}

	public function komentarTidak_get(){
		$id_tempat = $this->get('id_tempat');
		$komentar = $this->db->query("SELECT count(id_komentar) as jml_tidak FROM komentar JOIN sewa ON sewa.id_sewa=komentar.id_sewa JOIN tempat_sewa ON sewa.id_tempat=tempat_sewa.id_tempat WHERE suka='tidak' AND sewa.id_tempat='$id_tempat'")->result();
		$this->response($komentar,200);
	
	}

}