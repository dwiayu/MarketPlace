<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class TempatSewa extends REST_Controller {
    // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';

	function alamat_post(){
		$id_user = $this->post('id_user');
		$query= $this->db->query("SELECT id_alamat,alamat from alamat where id_user=$id_user")->result();
        $this->response(
            array(
                "status" => "success",
                "result" => $query
            )
        );
    }
    function cekTempat_post(){
        $id_user= $this->post('id_user');
        $cek_tempat= $this->db->query("
        SELECT * from tempat_sewa where status='ya' and id_user=$id_user")->result();
        if(!empty($cek_tempat)){
            $this->response(
                array(
                    "status" =>"success",
                    "result" = $query
                )    
                );
        }
        else{
            $this->response(
                array(
                    "status"=>"kosong"
                )
                );
        }
    }
    function statusIdentitas_post(){
        $id_user=$this->post('id_user');
        $getStatusID =$this->db->query("
        SELECT * from identitas join user ON user.id_user = identitas.id_user WHERE status!='valid' AND user.id_user=$id_user")->result();
        $getStatusID1 =$this->db->query("
        SELECT * from identitas join user ON user.id_user = identitas.id_user WHERE user.id_user=$id_user")->result();
      

        if(!empty($getStatusID) ){
            $this->response(array(
                "status" =>"success",
                "result" =>$getStatusID
            ));
        }else if(empty($getStatusID1)){
            $this->response(array(
                "status" =>"success",
                "result" =>$getStatusID
            ));
            
        }else{
            $this->response(array(
                "status" =>"valid",
                "result" =>$getStatusID
            ));
        }
       
    }
    function getTempat_post(){
        $id_user= $this->post('id_user');
        $getTempat = $this->db->query("
        SELECT * FROM tempat_sewa WHERE id_user=$id_user")->result();
        if(!empty($getTempat)){
            $this->response(
                array(
                    "status" => "success",
                    "result" => $getTempat
                )
             );
           }else{
            $this->response(
                array(
                    "status" => "kosong"
                )
             );
           }
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
            "alamat"=>$this->post("alamat"),
            "nama_tempat" =>$this->post("nama_tempat"),
            "no_rekening" =>$this->post("no_rekening"), 
            "slogan_tempat" =>$this->post("slogan_tempat"),
            "deskripsi_tempat" =>$this->post("deskripsi_tempat"),
            "foto_tempat" => $this->post("foto_tempat"),
            "status_tempat" =>$this->post("status_tempat"),
            "username" =>$this->post("username"),
            "password" =>$this->post("password"),
            "email"=>$this->post("email"),
            "no_hp" =>$this->post("no_hp")
        );
            if (!empty($data_tempatSewa)){
                $data_tempatSewa['foto_tempat']= $this->uploadPhoto();
                $insert= $this->db->insert('tempat_sewa',$data_tempatSewa);
                if ($insert){
                  $this->response(
                      array("status"=>"success",
                      "result" => array($data_tempatSewa),
                      "message"=>$insert));   
                }
                
             }else{
                $this->response(array("status"=>"fail"));   
              }
          }
          function updateTempat_post(){
            $data_tempat= array(
                'id_tempat' => $this->post('id_tempat'),
                "alamat"=>$this->post("alamat"),
                "nama_tempat" =>$this->post("nama_tempat"),
                "no_rekening" =>$this->post("no_rekening"), 
                "slogan_tempat" =>$this->post("slogan_tempat"),
                "deskripsi_tempat" =>$this->post("deskripsi_tempat"),
                "foto_tempat" => $this->post("foto_tempat"),
                "status_tempat" =>$this->post("status_tempat"),
                "username" =>$this->post("username"),
                "password" =>$this->post("password"),
                "email"=>$this->post("email"),
                "no_hp" =>$this->post("no_hp")
            );
            // Cek apakah ada di database
            $get_user_baseID = $this->db->query("
            SELECT 1
            FROM tempat_sewa
            WHERE id_tempat = {$data_tempat['id_tempat']}")->num_rows();

        if($get_user_baseID === 0){
            // Jika tidak ada
            $this->response(
                array(
                    "status"  => "failed",
                    "message" => "ID tempat tidak ditemukan"
                )
            );
        } else {
            // Jika ada
            $data_tempat['foto_tempat'] = $this->uploadPhoto();

            if ($data_tempat['foto_tempat']){
                $get_photo_tempat =$this->db->query("
                    SELECT foto_tempat
                    FROM tempat_sewa
                    WHERE id_tempat = {$data_tempat['id_tempat']}")->result();

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
                    id_alamat= '{$data_tempat['id_alamat']}',
                    nama_tempat= '{$data_tempat['nama_tempat']}',
                    no_rekening= '{$data_tempat['no_rekening']}',
                    slogan_tempat= '{$data_tempat['slogan_tempat']}',
                    deskripsi_tempat= '{$data_tempat['deskripsi_tempat']}',
                    foto_tempat = '{$data_tempat['foto_tempat']}',
                    status_tempat = '{$data_tempat['status_tempat']}',
                    username = '{$data_tempat['username']}',
                    passoword = '{$data_tempat['password']}',
                    email = '{$data_tempat['email']}',
                    no_hp = '{$data_tempat['no_hp']}'
                    WHERE id_tempat = '{$data_tempat['id_tempat']}'");

            } else {
                // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
                $update = $this->db->query("
                    UPDATE tempat_sewa
                    SET
                    id_alamat= '{$data_tempat['id_alamat']}',
                    nama_tempat= '{$data_tempat['nama_tempat']}',
                    no_rekening= '{$data_tempat['no_rekening']}',
                    slogan_tempat= '{$data_tempat['slogan_tempat']}',
                    deskripsi_tempat= '{$data_tempat['deskripsi_tempat']}',
                    status_tempat = '{$data_tempat['status_tempat']}',
                    username = '{$data_tempat['username']}',
                    passoword = '{$data_tempat['password']}',
                    email = '{$data_tempat['email']}',
                    no_hp = '{$data_tempat['no_hp']}'
                    WHERE id_tempat = {$data_tempat['id_tempat']}"
                );
            }

            if ($update){
                $this->response(
                    array(
                        "status"    => "success",
                        "result"    => array($data_tempat),
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