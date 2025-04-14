const editForm = (member) => {
  return `
    <div class="container mt-5">
      <div class="col-sm-8 mx-auto">
        <form id="updateForm">
          <fieldset>
            <legend class="h2 mb-4">修改會員資料</legend>
    
            <div class="mb-3 row">
              <label for="memberId" class="col-sm-2 col-form-label">會員編號:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  readonly class="form-control-plaintext"
                  name="memberId"
                  value="${member.memberId}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="name" class="col-sm-2 col-form-label">姓名:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  class="form-control form-control-md w-50"
                  name="name"
                  value="${member.name}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="gender" class="col-sm-2 col-form-label"
                >性別:</label
              >
              <div class="col-sm-10">
                <input
                  type="text"
                  readonly
                  class="form-control-plaintext"
                  name="gender"
                  value="${member.gender}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="idno" class="col-sm-2 col-form-label">身分證字號:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  readonly class="form-control-plaintext"
                  name="idno"
                  maxlength="10"
                  pattern="[A-Z]{1}[1-2,8-9]{1}\\d{8}"
                  value="${member.idno}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="email" class="col-sm-2 col-form-label">Email:</label>
              <div class="col-sm-10">
                <input
                  type="email"
                  class="form-control form-control-md"
                  name="email"
                  value="${member.email}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="phone" class="col-sm-2 col-form-label">電話:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  class="form-control form-control-md w-50"
                  name="phone"
                  maxlength="10"
                  pattern="^09\\d{8}"
                  value="${member.phone}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="birthday" class="col-sm-2 col-form-label">生日:</label>
              <div class="col-sm-10">
                <input
                  type="date"
                  readonly class="form-control-plaintext"
                  name="birthday"
                  value="${member.birthday}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="account" class="col-sm-2 col-form-label">帳號:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  readonly class="form-control-plaintext"
                  name="account"
                  maxlength="50"
                  value="${member.account}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="password" class="col-sm-2 col-form-label">密碼:</label>
              <div class="col-sm-10">
                <input
                  type="text"
                  class="form-control form-control-md w-75"
                  name="password"
                  maxlength="20"
                  value="${member.password}"
                  required
                />
              </div>
            </div>
    
            <div class="mb-3 row">
              <label for="active" class="col-sm-2 col-form-label">活耀狀態:</label>
              <div class="col-sm-10">
                <div class="form-check form-check-inline">
                  <input
                    type="radio"
                    class="form-check-input"
                    name="active"
                    value="1"
                    ${member.active ? "checked" : ""}
                    required
                  />
                  <label class="form-check-label">開啟</label>
                </div>
                <div class="form-check form-check-inline">
                  <input
                    type="radio"
                    class="form-check-input"
                    name="active"
                    value="0"
                    ${!member.active ? "checked" : ""}
                    required
                  />
                  <label class="form-check-label">凍結</label>
                </div>
              </div>
            </div>
    
            <div class="d-flex justify-content-center">
              <button type="submit" class="btn btn-primary mx-2">
                確認修改
              </button>
              <button type="reset" class="btn btn-secondary mx-2">
                清除
              </button>
            </div>
          </fieldset>
        </form>
    
        <div class="d-flex flex-row-reverse">
          <button class="btn btn-outline-secondary returnBtn">返回</button>
        </div>
      </div>
    </div>`;
};
