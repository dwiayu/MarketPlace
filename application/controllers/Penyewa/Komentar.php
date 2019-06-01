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

	// public function komentarYa_get(){
	// 	$id_tempat = $this->get('id_tempat');
	// 	$komentar = $this->db->query("SELECT count(id_komentar) as jml_ya FROM komentar JOIN sewa ON sewa.id_sewa=komentar.id_sewa JOIN tempat_sewa ON sewa.id_tempat=tempat_sewa.id_tempat WHERE suka='ya' AND sewa.id_tempat='$id_tempat'")->result();
	// 	$this->response($komentar,200);
	
	// }

	// public function komentarTidak_get(){
	// 	$id_tempat = $this->get('id_tempat');
	// 	$komentar = $this->db->query("SELECT count(id_komentar) as jml_tidak FROM komentar JOIN sewa ON sewa.id_sewa=komentar.id_sewa JOIN tempat_sewa ON sewa.id_tempat=tempat_sewa.id_tempat WHERE suka='tidak' AND sewa.id_tempat='$id_tempat'")->result();
	// 	$this->response($komentar,200);
	
	// }
	function getRiwayat_post(){
        $id_user = $this->post('id_user');
        $riwayat = $this->db->query("
            SELECT denda.jumlah_denda,log.id_detail,alamat.alamat,tempat_sewa.id_user ,
            user.nama,sewa.tgl_transaksi,kostum.nama_kostum,detail.jumlah,kostum.harga_kostum,log.status_log
             FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa 
            JOIN log ON detail.id_detail = log.id_detail
            JOIN kostum ON kostum.id_kostum = detail.id_kostum
            JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat
            JOIN user ON user.id_user = tempat_sewa.id_user
            JOIN alamat ON sewa.id_alamat = alamat.id_alamat
			join denda on denda.id_detail=log.id_detail
			joi sewa on sewa.id_user= user.id_user
            WHERE sewa.id_user='$id_user' AND status_log='selesai';
          ")->result();
	   $this->response(array('status'=>'success',"result"=>$riwayat)); 
	}
	function tambahKomentar_post(){
		$data_komentar= array(
			"id_user" =>$this->post("id_user"),
			"id_detail"=>$this->post("id_detail"),
			"komentar" =>$this->post("komentar")
		);
		if(empty($data_komentar['id_user'])){
			$this->response(array('status'=>'fail',"message"=>"id_user kosong"));
		}else{
			$getId_detail = $this->db->query("SELECT id_detail from detail WHERE id_detail='".$data_komentar['id_detail']."'")->result();
			$message="";
			if(empty($getId_detail)) $message.="id detail tidak ada";		
		}
		if(empty($message)){
			$insert= $this->db->insert('komentar', $data_komentar);
			if($insert){
                $this->response(
                    array(
                        "status"    => "success",
                        "result"    => array($data_komentar),
                        "message"   => $insert
                    )
                );
            }else{
                $this->response(array('status'=>'fail',"message"=>$message));
            }
		}
	} 
}