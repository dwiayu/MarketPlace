<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Tempat_sewa extends REST_Controller {
	private $folder_upload='uploads/';
	function __construct() {
        parent::__construct();
        $this->load->model("ModelLogin", "mdl");
	}
	
	// function index_get(){
	// 	$id_user = $this->get('id_user');
	// 	$query =$this->db->query("SELECT user.nama, user.email, user.no_hp,
	// 	  identitas.foto_ktp, identitas.foto_diri, identitas.status
	// 	 FROM user join identitas  ON user.id_user=identitas.id_user AND user.id_user='$id_user'")->result();
	// 	$this->response($query,200);

	// }
	function alamat_get(){
		$id_user = $this->get('id_user');
		$query= $this->db->query("SELECT alamat from alamat where id_user='$id_user'")->result();
		$this->response($query,200);
	}
	function all_get(){
		$id_user = $this->get('id_user');
		$query =$this->db->query("SELECT *
		 FROM tempat_sewa ts join user us ON ts.id_user=us.id_user AND ts.id_user='$id_user'")->result();
		$this->response($query,200);
	}

	function all_post(){
		$action = $this->post('action');
		$izin= 'Ya';
        $data_tempat = array(
			'id_tempat' =>$this->post('id_tempat'),
			'id_user' =>$this->post('id_user'),
			'id_alamat' =>$this->post('id_alamat'),
			'nama_tempat' => $this->post('nama_tempat'),
			'no_rekening' =>$this->post('no_rekening'),
			'slogan_tempat' =>$this->post('slogan_tempat'),
			'deskripsi_tempat'=>$this->post('deskripsi_tempat'),
		   'foto_tempat' =>$this->post('foto_tempat'),
		   'status_tempat' =>$this->post('status_tempat'),
		   'izin' =>($izin)
            // 'foto_tempat' => $this->post('foto_tempat')
        );
        switch ($action){
            case 'insert';
            $this->insertTempat($data_tempat);
            break;

            case 'update': 
            $this->updatetempat($data_tempat);
            break; 

            case 'delete' : 
            $this->deletetempat($data_tempat);
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
	function inserttempat($data_tempat){
        
                // Cek validasi
				if (empty($data_tempat['nama_tempat']) || empty($data_tempat['no_rekening']) || empty($data_tempat['slogan_tempat'])
				|| empty($data_tempat['deskripsi_tempat'])|| empty($data_tempat['status_tempat'])){
                    $this->response(
                        array(
                            "status" => "failed",
                            "message" => "Lengkapi Data"
                        )
                    );
                } else {
        
                    $data_tempat['foto_tempat'] = $this->uploadPhoto();
        
                    $do_insert = $this->db->insert('tempat_sewa', $data_tempat);
                   
                    if ($do_insert){
                        $this->response(
                            array(
                                "status" => "success",
                                "result" => array($data_tempat),
                                "message" => $do_insert
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
	function updatetempat($data_tempat){
		if(empty($data_tempat['nama_tempat']) || empty($data_tempat['no_rekening']) ||
			empty($data_tempat['slogan_tempat']) || empty($data_tempat['deskripsi_tempat'])|| empty($data_tempat['status_tempat'])){
				$this->response(
				array(
					"status" =>"failed",
					"message" => "Lengkapi semua data"             
					)
					);
		}else {
			$get_tempat_baseID= $this->db->query("
				SELECT id_tempat
				FROM tempat_sewa WHERE id_tempat = {$data_tempat['id_tempat']}")->num_rows();
					if ($get_tempat_baseID ==0){
						$this->response(
					array(
							"status" => "failed",
							"message" =>"ID tempat tidak ditemukan"
						)
						);
					}else{
						$data_tempat['foto_tempat'] = $this->uploadPhoto();
							if($data_tempat['foto_tempat']){
								$update = $this->db->query(" 
									UPDATE tempat_sewa SET 
									nama_tempat = '{$data_tempat['nama_tempat']}',
									no_rekening = '{$data_tempat['no_rekening']}',
									slogan_tempat = '{$data_tempat['slogan_tempat']}',
									deskripsi_tempat = '{$data_tempat['deskripsi_tempat']}',
									foto_tempat = '{$data_tempat['foto_tempat']}'
									WHERE id_tempat = '{$data_tempat['id_tempat']}'");
							}else{
								$update = $this->db->query("
									UPDATE tempat
									SET
										nama_tempat = '{$data_tempat['nama_tempat']}',
										no_rekening ='{$data_tempat['no_rekening']}',
										slogan_tempat  = '{$data_tempat['slogan_tempat']}',
										deskripsi_tempat = '{$data_tempat['deskripsi_tempat']}',
									WHERE id_tempat = {$data_tempat['id_tempat']}"
								);
								}
								if($update){
									$this->response(
										array(
											"status" => "success", 
											"result" => array ($data_tempat),
											"message" =>$update
										)
										);
								}
							}
						}
					} 
		function deletetempat($data_tempat){
			if(empty($data_tempat['id_tempat'])){
				$this->response(
					array(
						"status" =>"failed",
						"message" =>"ID tempat harus diisi"
					)
					);
			}else{
				$get_tempat_baseID = $this->db->query("
				SELECT id_tempat
				FROM tempat_sewa
				WHERE id_tempat = {$data_tempat['id_tempat']}")->num_rows(); 
				if($get_tempat_baseID>0){
					$get_foto_tempat = $this->db->query("
					SELECT foto_tempat
					FROM tempat_sewa
					WHERE id_tempat = {$data_tempat['id_tempat']}")->result();
	
					if(!empty($get_foto_tempat)){
						$photo_nama_file = basename($get_foto_tempat[0]->foto_tempat);
						$photo_lokasi_file = realpath(FCPATH . $this->folder_upload . $photo_nama_file);
	
					if(file_exists($photo_lokasi_file)){
						unlink($photo_lokasi_file);
					}
					$this->db->query("
						DELETE FROM tempat_sewa 
						WHERE id_tempat = {$data_tempat['id_tempat']}");
						$this->response(
							array(
								"status" =>"success", 
								"message" => "Data ID = " .$data_tempat['id_tempat']. "berhasil dihapus"
							)
							);
					}
				}else{
					$this->response(
						array(
							"status" => "failed",
							"message" => "ID tempat tidak ditemukan"
						)
						);
				}
			}
		}
}
