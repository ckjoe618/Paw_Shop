const table = new DataTable("#memberTable", {
  layout: {
    top1Start: {
      buttons: [
        {
          text: "新增會員",
          action: () => {
            $("#div1").hide();
            $("#div2").empty();
            $("#div2").append(addForm);
          },
        },
      ],
    },
  },
  columns: [
    { data: "memberId" },
    { data: "name" },
    { data: "gender" },
    { data: "idno" },
    { data: "email" },
    { data: "phone" },
    { data: "birthday" },
    { data: "account" },
    { data: "password" },
    { data: "createAccount" },
    { data: "active" },
    {
      data: "action",
      render: () => {
        const editBtn = `<button id="editBtn" class="btn btn-outline-primary btn-sm">編輯</button>`;
        const delBtn = `<button id="delBtn" class="btn btn-outline-danger btn-sm">刪除</button>`;
        return editBtn + delBtn;
      },
    },
  ],
  columnDefs: [
    {
      targets: "_all",
      className: "text-center",
    },
  ],
  createdRow: function (row, data, dataIndex) {
    $(row).attr("id", "row-" + data.memberId);
  },
});

const add = () => {
  $(document).on("submit", "#addForm", (e) => {
    e.preventDefault();
    apiAddMember($("#addForm").serialize())
      .then((member) => {
        table.row.add(member.data).draw();
        $("#div1").show();
        $("#div2").empty();
      })
      .catch((error) => alert(error));
  });
};

const edit = () => {
  $(document).on("click", "#editBtn", function () {
    const memberId = $(this).closest("tr").find("td:eq(0)").text();
    apiFindMemberType(memberId)
      .then((member) => {
        $("#div1").hide();
        $("#div2").empty();
        $("#div2").append(editForm(member.data));
        $(document).on("submit", "#updateForm", (e) => {
          e.preventDefault();
          apiUpdateMember($("#updateForm").serialize())
            .then((member) => {
              if (member.data.active == 1) {
                table
                  .row("#row-" + member.data.memberId)
                  .data(member.data)
                  .draw();
                $("#div1").show();
                $("#div2").empty();
              } else {
                table
                  .row("#row-" + member.data.memberId)
                  .remove()
                  .draw();
                $("#div1").show();
                $("#div2").empty();
              }
            })
            .catch((error) => alert(error));
        });
      })
      .catch((error) => alert(error));
  });
};

const del = () => {
  $(document).on("click", "#delBtn", function () {
    const memberId = $(this).closest("tr").find("td:eq(0)").text();
    apiDeleteMember(memberId)
      .then((member) => {
        table
          .row("#row-" + member.data.memberId)
          .remove()
          .draw();
      })
      .catch((error) => alert(error));
  });
};
