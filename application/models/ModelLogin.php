<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class ModelLogin extends CI_Model {
    public function __construct()
    {
        parent::__construct();
        //Do your magic here
    }
    public function login( $us, $ps)	
	{
	   // print_r($ps);
	   // die();
		
		$this->db->where('username',  $us);
		$this->db->where('password', $ps);
		$query = $this->db->get('user');
		return $query->result_array();
		
	}
	
}

/* End of file PegawaiModel.php */

?>