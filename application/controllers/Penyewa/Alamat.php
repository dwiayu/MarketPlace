<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Alamat extends REST_Controller {
 // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';
	function __construct() {
        parent::__construct();
        
	}
	

	function myAlamat_post(){
        $id_user = $this->post('id_user');
        $get_alamat = $this->db->query("
            SELECT id_alamat,id_user, label_alamat,alamat,kota
            FROM alamat WHERE id_user=$id_user")->result();
        $this->response(
           array(
               "status" => "success",
               "result" => $get_alamat
           )
       );
    }
    
    function all_post() {
                $action  = $this->post("action");
                $data_alamat = array(
                    'id_alamat' => $this->post('id_alamat'),
                    'id_user' =>$this->post('id_user'),
                    'label_alamat'   => $this->post('label_alamat'),
                    'alamat' =>$this->post('alamat'), 
                    'kota'=>$this->post('kota')
                );
        
                switch ($action) {
                    case 'insert':
                    $this->inputAlamat($data_alamat);
                    break;
                    
                    case 'update':
                    $this->updateAlamat($data_alamat);
                    break;
                    
                    case 'delete':
                    $this->deleteAlamat($data_alamat);
                    break;
                    
                    default:
                    $this->response(
                        array(
                            "status"  =>"failed",
                            "message" => "action harus diisi"
                        )
                    );
                    break;
                }
            }
    function inputAlamat($data_alamat){
        if (empty($data_alamat['id_user']) ||  empty($data_alamat['label_alamat']) ||
            (empty($data_alamat['alamat']) || empty($data_alamat['kota']))){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Data tidak lengkap"
                )
            );
        }else{
            $do_insert = $this->db->insert('alamat', $data_alamat);
            if ($do_insert){
            $this->response(
                array(
                    "status" => "success",
                    "result" => array($data_alamat),
                    "message" => $do_insert
                )
            );
            }
        }
    }
    function updateAlamat($data_alamat){
        $update = $this ->db->query("
        UPDATE alamat SET
        label_alamat = '{$data_alamat['label_alamat']}',
        alamat ='{$data_alamat['alamat']}',
        kota  = '{$data_alamat['kota']}'
        WHERE id_alamat = {$data_alamat['id_alamat']}"
            );
            if ($update){
                $this->response(
                    array(
                        "status"    => "success",
                        "result"    => array($data_alamat),
                        "message"   => $update
                    )
                );
            }else{
                $this->response(
                    array(
                        "status"    => "failed",
                        "message" => "gagal update"
                    )
                );
            }
    }
    function deletemy_post()
    {
      $id_alamat = $this->post('id_alamat');
   
      $this->db->where('id_alamat', $id_alamat);
      $delete = $this->db->delete('alamat');  
      if ($this->db->affected_rows()) {
        $this->response(array('status' => 'success','message' =>"Berhasil delete dengan id_alamat = ".$id_alamat));
      } else {
        $this->response(array('status' => 'fail', 'message' =>"id_alamat tidak dalam database"));
      } 
   
    } 
   
    function editalamat_post()
    {
      $data_alamat = array(
       'id_alamat'    => $this->post('id_alamat'),
       'label_alamat'    => $this->post('label_alamat'),
       'alamat' =>$this->post('alamat'),
       'kota'     => $this->post('kota'),
     );
      $this->db->where('id_alamat',$data_alamat['id_alamat']);
      $update= $this->db->update('alamat',$data_alamat);
      if ($update){
       $this->response(array('status'=>'success',"message"=>$update));
    
     }else{
       $this->response(array('status'=>'fail',"message"=>$message));   
     }
    }


    
}
