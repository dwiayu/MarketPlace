$(function() {
  $.ajax({
    type: "GET",
    url: "getAllPegawai/"
  }).done(function(countries) {
    countries.unshift({ id: "0", name: "" });

    $("#jsGrid").jsGrid({
      height: "300px",
      width: "100%",
      filtering: true,
      inserting: true,
      editing: true,
      sorting: true,
      paging: true,
      autoload: true,
      pageSize: 10,
      pageButtonCount: 5,
      deleteConfirm: "Do you really want to delete client?",
      controller: {
        loadData: function(filter) {
          return $.ajax({
            type: "GET",
            url: "getAllPegawai/",
            data: filter
          });
        },
        insertItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "addPegawai/",
            data: item
          });
        },
        updateItem: function(item) {
          return $.ajax({
            type: "PUT",
            url: "/clients/",
            data: item
          });
        },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "deletePegawai/",
            data: item
          });
        }
      },
      fields: [
        {
          name: "namaPegawai",
          title: "Nama PEgawai",
          type: "text",
          width: 150
        },
        {
          name: "alamatPegawai",
          title: "Alamat Pegawai",
          type: "text",
          width: 50
        },
        { type: "control" }
      ]
    });
  });
});
