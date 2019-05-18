<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class TempatSewa extends REST_Controller {
 // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
	}
	
    function tampilTempat_post(){
        $id_user = $this->post('id_user');
        $get_tempatSewa = $this->db->query("SELECT t.id_tempat,t.id_user,t.nama_tempat,t.no_rekening,
        t.slogan_tempat, t.deskripsi_tempat,t.foto_tempat, t.status_tempat, t.izin, a.alamat FROM tempat_sewa t join alamat a
        WHERE t.id_alamat=a.id_alamat AND t.id_user= $id_user")->result();
            if(!empty($get_tempatSewa)){
                $this->response(
                    array(
                        "status" =>"success",
                        "result"=>$get_tempatSewa
                    )
                    );
               
            }else{
                $this->response(
                    array(
                        "status"=>"kosong"
                    )
                    );
            }
    }
    function insertTempat_post(){
        $data_tempatSewa = array(
            "id_tempat" =>$this->post("id_tempat"),
            "id_user" =>$this->post("id_user"),
            "id_alamat"=>$this->post("id_alamat"),
            "nama_tempat" =>$this->post("nama_tempat"),
            "no_rekening" =>$this->post("no_rekening"), 
            "slogan_tempat" =>$this->post("slogan_tempat"),
            "deskripsi_tempat" =>$this->post("deskripsi_tempat"),
            "foto_tempat" => $this->post("foto_tempat"),
            "status_tempat" =>$this->post("status_tempat"),
            "izin" =>$this->post("izin")
        );
        if (empty($data_tempatSewa['id_alamat'])){
            $this->response(array('status'=>'fail',"message"=>"id_alamat kosong"));
          }
          else{
              $getid_alamat = $this->db->query("SELECT id_alamat from alamat WHERE id_alamat='".$data_tempatSewa['id_alamat']."'")->result();
              $getid_user = $this->db->query("SELECT id_user from user where id_user='".$data_tempatSewa['id_user']."'")->result();
              $message="";
              if (empty($getid_alamat)) $message.="id_alamat tidak ada/salah ";
              if (empty($getid_user)) {
                if (empty($message)) {
                  $message.="id_user tidak ada/salah";
                }
                else {
                  $message.="dan id_user tidak ada/salah";
                }
              }
              if (empty($message)){
                $data_tempatSewa['foto_tempat']= $this->uploadPhoto();
                $insert= $this->db->insert('tempat_sewa',$data_tempatSewa);
                if ($insert){
                  $this->response(
                      array('status'=>'success',
                      'result' => array($data_tempatSewa),
                      "message"=>$insert));   
                }
                
              }else{
                $this->response(array('status'=>'fail',"message"=>$message));   
              }
              
            }
            
          }
          function updateTempat_post(){
              $data_tempatSewa= array(
                'id_tempat' =>$this->post('id_tempat'),
                'id_user' =>$this->post('id_user'),
                'id_alamat'=>$this->post('id_alamat'),
                'nama_tempat' =>$this->post('nama_tempat'),
                'no_rekening' =>$this->post('no_rekening'), 
                'slogan_tempat' =>$this->post('slogan_tempat'),
                'deskripsi_tempat' =>$this->post('deskripsi_tempat'),
                'foto_tempat' => $this->post('foto_tempat'),
                'status_tempat' =>$this->post('status_tempat'),
                'izin' =>$this->post('izin')
              );
               // Cek apakah ada di database
            $get_user_baseID = $this->db->query("
            SELECT 1
            FROM tempat_sewa
            WHERE id_tempat = {$data_tempatSewa['id_tempat']}")->num_rows();

        if($get_user_baseID === 0){
            // Jika tidak ada
            $this->response(
                array(
                    "status"  => "failed",
                    "message" => "ID tempat tidak ditemukan"
                )
            );
              }else{
                
                   // Jika ada
            $data_tempatSewa['foto_tempat'] = $this->uploadPhoto();
            
                        if ($data_tempatSewa['foto_tempat']){
                            $get_photo_tempat =$this->db->query("
                                SELECT foto_tempat
                                FROM tempat_sewa
                                WHERE id_tempat = {$data_tempatSewa['id_tempat']}")->result();
            
                            if(!empty($get_photo_tempat)){
            
                                // Dapatkan nama_user file
                                $photo_nama_user_file = basename($get_photo_tempat[0]->foto_tempat);
                                // Dapatkan letak file di folder upload
                                $photo_lokasi_file = realpath(FCPATH . $this->folder_upload . $photo_nama_user_file);
            
                                // Jika file ada, hapus
                                if(file_exists($photo_lokasi_file)) {
                                    // Hapus file
                                    unlink($photo_lokasi_file);
                                }
                            }
                            // Jika upload foto berhasil, eksekusi update
                            $update = $this->db->query("
                                UPDATE tempat_sewa SET
                                id_tempat='{$data_tempatSewa['id_tempat']}',
                                id_alamat ='{$data_tempatSewa['id_alamat']}',
                                nama_tempat='{$data_tempatSewa['nama_tempat']}',
                                no_rekening='{$data_tempatSewa['no_rekening']}',
                                slogan_tempat='{$data_tempatSewa['slogan_tempat']}',
                                deskripsi_tempat='{$data_tempatSewa['deskripsi_tempat']}',
                                foto_tempat = '{$data_tempatSewa['foto_tempat']}',
                                status_tempat = '{$data_tempatSewa['status_tempat']}',
                                izin = '{$data_tempatSewa['izin']}'
                                WHERE id_tempat= '{$data_tempatSewa['id_tempat']}'");
            
                        } else {
                            // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
                            $update = $this->db->query("
                                UPDATE tempat_sewa
                                SET
                                id_tempat='{$data_tempatSewa['id_tempat']}',
                                id_alamat ='{$data_tempatSewa['id_alamat']}',
                                nama_tempat='{$data_tempatSewa['nama_tempat']}',
                                no_rekening='{$data_tempatSewa['no_rekening']}',
                                slogan_tempat='{$data_tempatSewa['slogan_tempat']}',
                                deskripsi_tempat='{$data_tempatSewa['deskripsi_tempat']}',
                                status_tempat = '{$data_tempatSewa['status_tempat']}',
                                izin = '{$data_tempatSewa['izin']}'
                                WHERE id_tempat= '{$data_tempatSewa['id_tempat']}'"
                            );
                        }
            
                        if ($update){
                            $this->response(
                                array(
                                    "status"    => "success",
                                    "result"    => array($data_tempatSewa),
                                    "message"   => $update
                                )
                            );
                        }
                    }
                    }
    function uploadPhoto() {
        
                // Apakah user upload gambar?
                if ( isset($_FILES['foto_tempat']) && $_FILES['foto_tempat']['size'] > 0 ){
        
                    // Foto disimpan di android-api/uploads
                    $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
                    $config['allowed_types'] = 'jpg|png';
        
                    // Load library upload & helper
                    $this->load->library('upload', $config);
                    $this->load->helper('url');
        
                    // Apakah file berhasil diupload?
                    if ( $this->upload->do_upload('foto_tempat')) {
        
                       // Berhasil, simpan nama_user file-nya
                       $img_data = $this->upload->data();
                       $post_image = $img_data['file_name'];
        
                   } else {
        
                        // Upload gagal, beri nama_user image dengan errornya
                        // Ini bodoh, tapi efektif
                    $post_image = $this->upload->display_errors();
        
                }
            } else {
                    // Tidak ada file yang di-upload, kosongkan nama_user image-nya
                $post_image = '';
            }
        
            return $post_image;
        }

}
