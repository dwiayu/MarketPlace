<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Pendaftaran extends REST_Controller {
	private $folder_upload='uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
    }

	function all_post(){
		$level = 'Toko';
		$action = $this->post('action');
		$data_pendaftaran = array(
			'id_user' =>$this->post('id_user'),
			'nama' => $this->post('nama'),
			'jenis_kelamin' => $this->post('jenis_kelamin'),
			'email' => $this->post('email'),
			'no_hp' =>$this->post('no_hp'),
			'foto_user'=>$this->post('foto_user'),
			'username' =>$this->post('username'),
			'password' =>$this->post('password'),
			'level' => ($level)
		);
		switch ($action){
			case 'insert';
			$this->insertPendaftaran($data_pendaftaran);
			break;

			default: 
			$this->response (
				array(
					"status" =>"failed", 
					"message" =>"action harus diisi"
				)
				);
				break;
		}
	}
	function insertPendaftaran($data_pendaftaran){
		
				// Cek validasi
				if (empty($data_pendaftaran['nama'])||empty($data_pendaftaran['jenis_kelamin'])||empty($data_pendaftaran['email']) || empty($data_pendaftaran['no_hp']) ||
				empty($data_pendaftaran['username']) || empty($data_pendaftaran['password'])){
					$this->response(
						array(
							"status" => "failed",
							"message" => "Lengkapi Data"
						)
					);
				} 
				else {
		
					$data_pendaftaran['foto_user'] = $this->uploadPhoto();
		
					$do_insert = $this->db->insert('user', $data_pendaftaran);
				   
					if ($do_insert){
						$this->response(
							array(
								"status" => "success",
								"result" => array($data_pendaftaran),
								"message" => $do_insert
							)
						);
					}
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
		
					   // Berhasil, simpan nama file-nya
					   // URL image yang disimpan adalah http://localhost/android-api/uploads/namafile
					   $img_data = $this->upload->data();
					   $post_image = base_url(). $this->folder_upload .$img_data['file_name'];
		
					} else {
		
						// Upload gagal, beri nama image dengan errornya
						// Ini bodoh, tapi efektif
						$post_image = $this->upload->display_errors();
						
					}
				} else {
					// Tidak ada file yang di-upload, kosongkan nama image-nya
					$post_image = '';
				}
		
				return $post_image;
			}
}
