<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Kostum extends REST_Controller {
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

	function all_post(){
		$action = $this->post('action');
        $data_kostum = array(
			'id_kostum' =>$this->post('id_kostum'),
			'id_kategori' =>$this->post('id_kategori'),
			'id_tempat' =>$this->post('id_tempat'),
			'nama_kostum' => $this->post('nama_kostum'),
			'jumlah_kostum' =>$this->post('jumlah_kostum'),
			'harga_kostum' =>$this->post('harga_kostum'),
			'deskripsi_kostum'=>$this->post('deskripsi_kostum'),
           'foto_kostum' =>$this->post('foto_kostum')
            // 'foto_kostum' => $this->post('foto_kostum')
        );
        switch ($action){
            case 'insert';
            $this->insertKostum($data_kostum);
            break;

            case 'update': 
            $this->updateKostum($data_kostum);
            break; 

            case 'delete' : 
            $this->deletekostum($data_kostum);
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
	function insertkostum($data_kostum){
        
                // Cek validasi
				if (empty($data_kostum['nama_kostum']) || empty($data_kostum['jumlah_kostum']) || empty($data_kostum['harga_kostum'])
				|| empty($data_kostum['deskripsi_kostum'])){
                    $this->response(
                        array(
                            "status" => "failed",
                            "message" => "Lengkapi Data"
                        )
                    );
                } else {
        
                    $data_kostum['foto_kostum'] = $this->uploadPhoto();
        
                    $do_insert = $this->db->insert('kostum', $data_kostum);
                   
                    if ($do_insert){
                        $this->response(
                            array(
                                "status" => "success",
                                "result" => array($data_kostum),
                                "message" => $do_insert
                            )
                        );
                    }
                }
            }
			function uploadPhoto() {
				
						// Apakah user upload gambar?
						if ( isset($_FILES['foto_kostum']) && $_FILES['foto_kostum']['size'] > 0 ){
				
							// Foto disimpan di android-api/uploads
							$config['upload_path'] = realpath(FCPATH . $this->folder_upload);
							$config['allowed_types'] = 'jpg|png';
				
							// Load library upload & helper
							$this->load->library('upload', $config);
							$this->load->helper('url');
				
							// Apakah file berhasil diupload?
							if ( $this->upload->do_upload('foto_kostum')) {
				
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
	function updatekostum($data_kostum){
		if(empty($data_kostum['nama_kostum']) || empty($data_kostum['jumlah_kostum']) ||
			empty($data_kostum['harga_kostum']) || empty($data_kostum['deskripsi_kostum'])){
				$this->response(
				array(
					"status" =>"failed",
					"message" => "Lengkapi semua data"             
					)
					);
		}else {
			$get_kostum_baseID= $this->db->query("
				SELECT id_kostum
				FROM kostum WHERE id_kostum = {$data_kostum['id_kostum']}")->num_rows();
					if ($get_kostum_baseID ==0){
						$this->response(
					array(
							"status" => "failed",
							"message" =>"ID kostum tidak ditemukan"
						)
						);
					}else{
						$data_kostum['foto_kostum'] = $this->uploadPhoto();
							if($data_kostum['foto_kostum']){
								$update = $this->db->query(" 
									UPDATE kostum SET 
									nama_kostum = '{$data_kostum['nama_kostum']}',
									jumlah_kostum = '{$data_kostum['jumlah_kostum']}',
									harga_kostum = '{$data_kostum['harga_kostum']}',
									deskripsi_kostum = '{$data_kostum['deskripsi_kostum']}',
									foto_kostum = '{$data_kostum['foto_kostum']}'
									WHERE id_kostum = '{$data_kostum['id_kostum']}'");
							}else{
								$update = $this->db->query("
									UPDATE kostum
									SET
										nama_kostum = '{$data_kostum['nama_kostum']}',
										jumlah_kostum ='{$data_kostum['jumlah_kostum']}',
										harga_kostum  = '{$data_kostum['harga_kostum']}',
										deskripsi_kostum = '{$data_kostum['deskripsi_kostum']}'
									WHERE id_kostum = {$data_kostum['id_kostum']}"
								);
								}
								if($update){
									$this->response(
										array(
											"status" => "success", 
											"result" => array ($data_kostum),
											"message" =>$update
										)
										);
								}
							}
						}
					} 
		function deleteKostum($data_kostum){
			if(empty($data_kostum['id_kostum'])){
				$this->response(
					array(
						"status" =>"failed",
						"message" =>"ID kostum harus diisi"
					)
					);
			}else{
				$get_kostum_baseID = $this->db->query("
				SELECT 1
				FROM kostum 
				WHERE id_kostum = {$data_kostum['id_kostum']}")->num_rows(); 
				if($get_kostum_baseID>0){
					$get_foto_kostum = $this->db->query("
					SELECT foto_kostum
					FROM kostum
					WHERE id_kostum = {$data_kostum['id_kostum']}")->result();
	
					if(!empty($get_foto_kostum)){
						$photo_nama_file = basename($get_foto_kostum[0]->foto_kostum);
						$photo_lokasi_file = realpath(FCPATH . $this->folder_upload . $photo_nama_file);
	
					if(file_exists($photo_lokasi_file)){
						unlink($photo_lokasi_file);
					}
					$this->db->query("
						DELETE FROM kostum 
						WHERE id_kostum = {$data_kostum['id_kostum']}");
						$this->response(
							array(
								"status" =>"success", 
								"message" => "Data ID = " .$data_kostum['id_kostum']. "berhasil dihapus"
							)
							);
					}
				}else{
					$this->response(
						array(
							"status" => "failed",
							"message" => "ID kostum tidak ditemukan"
						)
						);
				}
			}
		}
}
