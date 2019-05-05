<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class pegawai_model extends CI_Model {
    public function __construct()
    {
        parent::__construct();
        //Do your magic here
    }

    public function getAllPegawai()
    {
        $query = $this->db->get('pegawai');
        if($query->num_rows()>0){
            return $query->result();
        }
    }

    public function save()
    {
        $data= $this->input->post();
        $this->db->insert('pegawai', $data);
    }

    public function delete($id)
    {
        $this->db->where('idPegawai', $id);
        $this->db->delete('pegawai');
    }
}

/* End of file PegawaiModel.php */

?>