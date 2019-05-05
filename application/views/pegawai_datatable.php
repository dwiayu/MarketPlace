<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>CODE IGNITER 7</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

		<link rel="stylesheet" href="<?php echo base_url('') ?>assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="<?php echo base_url('') ?>assets/datatables.min.css">

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<h1><center>WELCOME PEGAWAI</h1></center>
		<div class="table-responsive">
			<table class="table table-hover" id="example">
		<thead>
			<tr>
				<th>Nama</th>
				<th>Nip</th>
				<th>Tanggal Lahir</th>
				<th>Action</th>
			</tr>
		</thead>
		<?php foreach ($pegawai_list as $key) { ?>
			<tr>
				<td><?php echo $key->namaPegawai ?></td>
				<td><?php echo $key->nip ?></td>
				<td><?php echo $key->tanggalLahir ?></td>
				<td>
					<a href="<?php echo site_url('jabatan/index/').$key->idpegawai ?>" type="button" class="btn btn-info">Jabatan</a>
					<a href="<?php echo site_url('anak/index/').$key->idpegawai ?>" type="button" class="btn btn-success">Anak</a>
				</td>
			</tr>
		<?php } ?>
		</tbody>
		</table>
	</div>


		<!-- jQuery -->
		<script src="//code.jquery.com/jquery.js"></script>
		<!-- Bootstrap JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

		<script src="<?php echo base_url('') ?>assets/js/bootstrap.min.js"></script>
		<script src="<?php echo base_url('') ?>assets/datatables.min.js"></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script type="text/javascript">
			$(document).ready(function() {
				$('#example').DataTable();
		} );
	</script>
 		<script src="Hello World"></script>
	</body>
</html>