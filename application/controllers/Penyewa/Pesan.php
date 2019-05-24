<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Pesan extends REST_Controller {
 // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
    }

function getPesan_post(){
    $id_user = $this->post('id_user');
    $pesan= $this->db->query("
        SELECT * FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa JOIN log ON log.id_detail = detail.id_detail
        JOIN kostum ON kostum.id_kostum = detail.id_kostum JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat 
        JOIN alamat ON alamat.id_alamat = sewa.id_alamat JOIN user ON sewa.id_user = user.id_user WHERE user.id_user='$id_user' AND 
        status_log = 'pesan' ;
    ")->result();
    $this->response(array('status'=>'success',
    "result"=>$pesan));
}

function getVerifikasi_post(){
    $id_user = $this->post('id_user');
    $pesan= $this->db->query("
        SELECT * FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa JOIN log ON log.id_detail = detail.id_detail
        JOIN kostum ON kostum.id_kostum = detail.id_kostum JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat 
        JOIN alamat ON alamat.id_alamat = sewa.id_alamat JOIN user ON sewa.id_user = user.id_user WHERE user.id_user='$id_user' AND 
        status_log = 'valid' OR status_sewa='batal';
    ")->result();
    $this->response(array('status'=>'success',
    "result"=>$pesan));
}

function getAmbilSewa_post(){
    $id_user = $this->post('id_user');
    $pesan= $this->db->query("
        SELECT * FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa JOIN log ON log.id_detail = detail.id_detail
        JOIN kostum ON kostum.id_kostum = detail.id_kostum JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat 
        JOIN alamat ON alamat.id_alamat = sewa.id_alamat JOIN user ON sewa.id_user = user.id_user WHERE user.id_user='$id_user' AND 
        status_log = 'ambil' OR status_log = 'transfer';
    ")->result();
    $this->response(array('status'=>'success',
    "result"=>$pesan));
}

function getRiwayatSewa_post(){
    $id_user = $this->post('id_user');
    $pesan= $this->db->query("
        SELECT * FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa JOIN log ON log.id_detail = detail.id_detail
        JOIN kostum ON kostum.id_kostum = detail.id_kostum JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat 
        JOIN alamat ON alamat.id_alamat = sewa.id_alamat JOIN user ON sewa.id_user = user.id_user WHERE user.id_user='$id_user' AND 
        status_log = 'selesai';
    ")->result();
    $this->response(array('status'=>'success',
    "result"=>$pesan));
}
    
    function getSewa_post(){
        $id_user=$this->post('id_user');
        $sewa= $this->db->query("
        SELECT log.id_log,tempat_sewa.id_user ,user.nama,sewa.tgl_transaksi,kostum.nama_kostum,detail.jumlah,kostum.harga_kostum,log.status_log,
        alamat.alamat FROM sewa JOIN detail ON sewa.id_sewa = detail.id_sewa 
        JOIN log ON detail.id_detail = log.id_detail
        JOIN kostum ON kostum.id_kostum = detail.id_kostum
        JOIN tempat_sewa ON tempat_sewa.id_tempat = kostum.id_tempat
        JOIN user ON user.id_user = tempat_sewa.id_user
        JOIN alamat ON sewa.id_alamat = alamat.id_alamat
        WHERE tempat_sewa.id_user='$id_user' AND status_log='ambil';
        ")->result();
        $this->response(array('status'=>'success',"result"=>$sewa));
    }
    function updateSewaSelesai_post(){
        $status_log='selesai';
        $id_log= $this->post('id_log');
        $array_data_log = array(
            'id_log' => $this->post('id_log'),
            'id_detail'=> $this->post('id_detail'),
            'status_log'=>($status_log)
        );
            //cek apakah data ada di database
    $get_user_baseID = $this->db->query("
    SELECT 1 FROM log WHERE id_log ={$array_data_log['id_log']}")->num_rows();
    if($get_user_baseID ===0){
        //jika tidak ada
        $this->response(
            array(
                "status"=>"failed",
                "message" =>"id log tidak ditemukan"
            )
            );
    }else{
        $updateSewa = $this->db->query("
        UPDATE log set
       status_log ='{$array_data_log['status_log']}'
        WHERE id_log ='{$array_data_log['id_log']}'
         ");
    }     if($updateSewa){
        $this->response(
            array(
                "status" =>"success",
                "result" =>array($array_data_log),
                "message"=>$updateSewa
            )
            );
    }
    }
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
            WHERE tempat_sewa.id_user='$id_user' AND status_log='selesai';
          ")->result();
       $this->response(array('status'=>'success',"result"=>$riwayat));
    }
    function inputDenda_post(){
        $id_log= $this->post('id_log');
        $data_Denda= array(
            'id_detail' =>$this->post('id_detail'),
            'jumlah_denda' => $this->post('jumlah_denda'),
            'keterangan' =>$this->post('keterangan')
        );
        if(empty($data_Denda['id_detail'])){
           $getId_detail = $this->db->query("SELECT id_detail FROM detail WHERE id_Detail='".$data_Denda['id_detail']."'")->result();
           $message="";
           if(empty($getId_detail)) $message.="id detail tidak ada";    
        }
        if(empty($message)){
           
            $insert= $this->db->insert('denda', $data_Denda);
            if($insert){
                $this->response(
                    array(
                        "status"    => "success",
                        "result"    => array($data_Denda),
                        "message"   => $insert
                    )
                );
            }else{
                $this->response(array('status'=>'fail',"message"=>$message));
            }
        }
    }
    function getKomentar_post(){
        $id_user  = $this->post('id_user');
        $get_komentar= $this->db->query("
        SELECT
        sewa.tgl_transaksi,user.nama,kostum.nama_kostum,komentar.komentar FROM 
komentar join detail on komentar.id_detail = detail.id_detail
join sewa on sewa.id_sewa = detail.id_sewa
join kostum on kostum.id_kostum= detail.id_kostum
join tempat_sewa on kostum.id_tempat=tempat_sewa.id_tempat
join user on user.id_user= sewa.id_user where tempat_sewa.id_user= $id_user")->result();
$this->response(array('status'=>'success',"result"=>$get_komentar));
    }
 
 

    }