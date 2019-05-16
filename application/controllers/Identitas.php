<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Identitas extends REST_Controller {
 // Konfigurasi letak folder untuk upload image
 private $folder_upload = 'uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
	}
	
	// function index_get(){
	// 	$id_user = $this->get('id_user');
	// 	// $query =$this->db->query("SELECT user.nama, user.email, user.no_hp,
	// 	//   identitas.foto_ktp, identitas.foto_diri, identitas.status
	// 	//  FROM user join identitas  ON user.id_user=identitas.id_user AND user.id_user='$id_user'")->result();
	// 	$query= $this->db->query("SELECT nama,alamat FROM user where id_user='$id_user'")->result();
	// 	$this->response($query,200);

	// }
    function all_get(){
        $get_identitas = $this->db->query("
            SELECT
            id_identitas,
            id_user,
            foto_ktp,
            status,
            FROM identitas")->result();
        $this->response(
           array(
               "status" => "success",
               "result" => $get_identitas
           )
       );
    }
    
    function all_post() {
$status= 'menunggu';
        $action  = $this->post("action");
        $data_identitas = array(
            'id_user' => $this->post('id_user'),
            'id_identitas' =>$this->post('id_identitas'),
            'foto_ktp'   => $this->post('foto_ktp'),
            'status' => ($status)
        );

        switch ($action) {
            case 'insert':
            $this->identitas($data_identitas);
            break;
            
            case 'update':
            $this->myidentitas($data_identitas);
            break;
            
            case 'delete':
            $this->deleteuser($data_identitas);
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
    function identitas($data_identitas){
        $status= 'menunggu';
        // $data_identitas = array(
        //     'id_identitas' =>$this->post('id_identitas'),
        //     'id_user' =>$this->post('id_user'),
        //     'foto_ktp' =>$this->post('foto_ktp'),
        //     'status' => ($status)
        // );
        if (empty($data_identitas['id_user']) ||  empty($data_identitas['status'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Data tidak lengkap"
                )
            );
        } else{
            $data_identitas['foto_ktp']= $this->uploadPhoto();
            $do_insert = $this->db->insert('identitas', $data_identitas);
            if ($do_insert){
            $this->response(
                array(
                    "status" => "success",
                    "result" => array($data_identitas),
                    "message" => $do_insert
                )
            );
            }
        }
        }

    function myidentitas($data_identitas){
       
        if(empty($data_identitas['id_identitas']) ){
            $this->response(
                array(
                    "status" =>"failed",
                    "message" => "Lengkapi Data"             
            )
                );
        }else{
//cek apakah data ada di database
$get_user_baseID= $this->db->query("
SELECT
*
  FROM identitas 
  WHERE id_identitas={$data_identitas['id_identitas']} limit 1")->num_rows();
//jika tidak ada
if($get_user_baseID ==0){
    $this->response(
        array (
            "status" =>"failed",
            "message" =>"user ID tidak ditemukan")
        );
}else{
    //jika ada
    $data_identitas['foto_ktp'] = $this->uploadPhoto();

    if($data_identitas['foto_ktp']){
        $get_foto_ktp = $this->db->query("
        SELECT foto_ktp from identitas WHERE id_identitas ={$data_identitas['id_identitas']}")->result();
    
        if(!empty($get_foto_ktp)){
            //Dapatkan nama user file
            $foto_nama_user_file = basename($get_foto_ktp[0]->foto_ktp);
            //dapatkan letakkan di folder upload 
            $foto_lokasi_file = realpath(FCPATH . $this->folder_upload . $foto_nama_user_file);

//             //jika file ada, hapus
            if(file_exists($foto_lokasi_file)){
                //hapus file
                unlink($foto_lokasi_file);
            }
        }
//         //jika upload foto berhasil, eksekusi update
        $update = $this ->db->query("
        UPDATE identitas SET
        foto_ktp ='{$data_identitas['foto_ktp']}'
        WHERE id_identitas = {$data_identitas['id_identitas']}"
            );

}

    $this->db->where('id_identitas', $data_identitas['id_identitas']);
    $update= $this->db->update ('identitas',$data_identitas);
    if($update){
        $this->response(
            array(
                'status'=>'success',
                'result'=>array($data_identitas),
                 "message"=>$update));
    }else{
    $this->response(
        array(
            "status"    => "failed",
            "message" => "gagal update"
        )
    );
}
        }

    }
      
    }

function myidentitas_post(){
    $id_user = $this->post('id_user');
    $get_identitas = $this->db->query("
    SELECT
   id_identitas,id_user, foto_ktp, status
       FROM identitas WHERE id_user=$id_user")->result();
$this->response(
   array(
       "status" => "success",
       "result" => $get_identitas
   )
);
}    

    function uploadPhoto() {
        
                // Apakah user upload gambar?
                if ( isset($_FILES['foto_ktp']) && $_FILES['foto_ktp']['size'] > 0 ){
        
                    // Foto disimpan di android-api/uploads
                    $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
                    $config['allowed_types'] = 'jpg|png';
        
                    // Load library upload & helper
                    $this->load->library('upload', $config);
                    $this->load->helper('url');
        
                    // Apakah file berhasil diupload?
                    if ( $this->upload->do_upload('foto_ktp')) {
        
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
