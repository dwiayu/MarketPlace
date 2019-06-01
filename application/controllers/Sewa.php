<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Sewa extends REST_Controller {
 // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
    }
    
    function insertSewa_post(){
        $tanggal= date("Y-m-d H:i:s");
        $data_sewa= array(
            "id_sewa" =>$this->post("id_sewa"),
            "id_user" =>$this->post("id_user"),
            "id_alamat" =>$this->post("id_alamat"),
            "tgl_transaksi" =>($tanggal),
            "tgl_sewa" => $this->post("tgl_sewa"),
            "tgl_kembali" =>$this->post("tgl_kembali")
        );
        $data_detail = array(
            "id_detail" =>$this->post("id_detail"),
            "id_sewa"=>$this->post("id_sewa"),
            "id_kostum" =>$this->post("id_kostum"),
            "jumlah" =>$this->post("jumlah")
        );
        if(empty($data_sewa['id_user'])){
            $this->response(array('status'=>'fail', "message"=>'id_user kosong'));
        }else if(empty($data_sewa['id_alamat'])){
            $this->response(array('status'=>'fail', "message"=>'id_alamat kosong'));
        }else{
            $getId_user= $this->db->query("SELECT id_user from id_user where id_user'".$data_sewa['id_user']."'")->result();
            $getId_alamat= $this->db->query("SELECT id_alamat from id_alamat where id_alamat'".$data_sewa['id_alamat']."'")->result();
            $message="";
            if(empty($getId_user)) $message.="id user tidak ada";
            if(empty($getId_alamat)){
                $message .="id_alamat tidak ada";
            }else{
                $message.="id_alamat tidak ada";
            }
        }
        if(empty($message)){
            $insert= $this->db->insert('sewa', $data_sewa);
            if($insert){
                $this->response(
                    array(
                        "status"=>"success",
                        "result" =>array($data_sewa),
                        "message" =>$insert
                    )
                    );
            }
            }else if(result($data_sewa)=="success"){
                $insert= $this->db->insert('detail', $data_detail);
                if($insert){
                    $this->response(
                        array(
                            "status" =>"success",
                            "result"=>array($data_detail),
                            "message"=>$insert
                        )
                        );
                }else{
                    $this->response(array('status'=>'fail',"message"=>$message));
                }
        }
    }
   
    }