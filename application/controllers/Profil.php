<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Profil extends REST_Controller {
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
        $get_user = $this->db->query("
            SELECT
            id_user,
            nama,
            jenis_kelamin,
            no_hp,
            email,
            username,
            password,
            level,
            foto_user
            FROM user")->result();
        $this->response(
           array(
               "status" => "success",
               "result" => $get_user
           )
       );
    }

	function myProfil_post(){
		$id_user = $this->post('id_user');
        $get_user = $this->db->query("
            SELECT
            id_user,
			nama,
			jenis_kelamin,
		   no_hp,
           foto_user,
           email,
            username,
            password,
            level
            FROM user WHERE id_user=$id_user")->result();
        $this->response(
           array(
               "status" => "success",
               "result" => $get_user
           )
       );
    }
    
    function all_post() {

        $action  = $this->post('action');
        $data_user = array(
            'id_user' => $this->post('id_user'),
            'nama'       => $this->post('nama'),
            'jenis_kelamin'     => $this->post('jenis_kelamin'),
            'no_hp' =>$this->post('no_hp'),
            'email' =>$this->post('email'),
            'username'      => $this->post('username'),
            'password'      => $this->post('password'),
            'level'      => $this->post('level'),
            'foto_user'   => $this->post('foto_user')
        );

        switch ($action) {
            case 'insert':
            $this->insertuser($data_user);
            break;
            
            case 'update':
            $this->updateuser($data_user);
            break;
            
            case 'delete':
            $this->deleteuser($data_user);
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
    function myedit_post(){
       
        $data_user = array(
            'id_user' =>$this->post('id_user'),
            'nama' =>$this->post('nama'),
            // 'jenis_kelamin' =>$this->post('jenis_kelamin'),
            'no_hp' =>$this->post('no_hp'),
            // 'foto_user' =>$this->post('foto_user'),
            'email' =>$this->post('email'),
            'username'=>$this->post('username'),
            'password' =>$this->post('password')
        );

        $update = $this ->db->query("
        UPDATE user SET
        nama = '{$data_user['nama']}',
        no_hp ='{$data_user['no_hp']}',
        email  = '{$data_user['email']}',
        username = '{$data_user['username']}',
        password = '{$data_user['password']}'
        WHERE id_user = {$data_user['id_user']}"
            );
        //cek apakah data ada di database
        // $get_user_baseID= $this->db->query("
        // SELECT
        //  1
        //   FROM user 
        //   WHERE id_user={$data_user['id_user']}")->num_rows();
        // //jika tidak ada
        // if($get_user_baseID ==0){
        //     $this->response(
        //         array (
        //             "status" =>"failed",
        //             "message" =>"user ID tidak ditemukan")
        //         );
        // }else{
            //jika ada
        //     $data_user['foto_user'] = $this->uploadPhoto();

        //     if($data_user['foto_user']){
        //         $get_foto_user = $this->db->query("
        //         SELECT foto_user from user WHERE id_user ={$data_user['id_user']}")->result();
            
        //         if(!empty($get_foto_user)){
        //             //Dapatkan nama user file
        //             $foto_nama_user_file = basename($get_foto_user[0]->foto_user);
        //             //dapatkan letakkan di folder upload 
        //             $foto_lokasi_file = realpath(FCPATH . $this->folder_upload . $foto_nama_user_file);

        //             //jika file ada, hapus
        //             if(file_exists($foto_lokasi_file)){
        //                 //hapus file
        //                 unlink($foto_lokasi_file);
        //             }
        //         }
        //         //jika upload foto berhasil, eksekusi update
        //         $update = $this ->db->query("
        //         UPDATE user SET
        //         nama = '{$data_user['nama']}',
        //         jenis_kelamin = '{$data_user['jenis_kelamin']}',
        //         no_hp ='{$data_user['no_hp']}',
        //         foto_user ='{$data_user['foto_user']}',
        //         email  = '{$data_user['email']}',
        //         username = '{$data_user['username']}',
        //         password = '{$data_user['password']}'
        //         WHERE id_user = {$data_user['id_user']}"
        //             );
     
        // }else{
        //      // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
        //      $update = $this ->db->query("
        //      UPDATE user SET
        //      nama = '{$data_user['nama']}',
            
        //      no_hp ='{$data_user['no_hp']}',
        //      email  = '{$data_user['email']}',
        //      username = '{$data_user['username']}',
        //      password = '{$data_user['password']}'
        //      WHERE id_user = {$data_user['id_user']}"
        //          );
      
        // }
        // if($update){
        //     $this->db->where('id_user', $data_user['id_user']);
        //     $update= $this->db->update ('user',$data_user);
        //     if($update){
        //         $this->response(
        //             array(
        //                 'status'=>'success',
        //                 'result'=>array($data_user),
        //                  "message"=>$update));
        //     }
        // }
        if ($update){
            $this->response(
                array(
                    "status"    => "success",
                    "result"    => array($data_user),
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


    function uploadPhoto() {
        
                // Apakah user upload gambar?
                if ( isset($_FILES['foto_user']) && $_FILES['foto_user']['size'] > 0 ){
        
                    // Foto disimpan di android-api/uploads
                    $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
                    $config['allowed_types'] = 'jpg|png';
        
                    // Load library upload & helper
                    $this->load->library('upload', $config);
                    $this->load->helper('url');
        
                    // Apakah file berhasil diupload?
                    if ( $this->upload->do_upload('foto_user')) {
        
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
