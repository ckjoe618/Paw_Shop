const addForm = `
        <div class="container mt-5">
          <div class="col-sm-8 mx-auto">
            <form id="addForm">
              <fieldset>
                <legend class="h2 mb-4">新增會員</legend>
                <div class="mb-3 row">
                  <label for="name" class="col-sm-2 col-form-label"
                    >姓名:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control form-control-md w-50"
                      name="name"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="gender" class="col-sm-2 col-form-label"
                    >性別:</label
                  >
                  <div class="col-sm-10">
                    <div class="form-check form-check-inline">
                      <input
                        type="radio"
                        class="form-check-input"
                        name="gender"
                        value="男"
                        checked
                        required
                      />
                      <label class="form-check-label">男</label>
                    </div>
                    <div class="form-check form-check-inline">
                      <input
                        type="radio"
                        class="form-check-input"
                        name="gender"
                        value="女"
                        required
                      />
                      <label class="form-check-label">女</label>
                    </div>
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="idno" class="col-sm-2 col-form-label"
                    >身分證字號:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      id="idno"
                      class="form-control form-control-md w-50"
                      name="idno"
                      maxlength="10"
                      pattern="[A-Z]{1}[1-2,8-9]{1}\\d{8}"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="email" class="col-sm-2 col-form-label"
                    >Email:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="email"
                      id="email"
                      class="form-control form-control-md"
                      name="email"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="phone" class="col-sm-2 col-form-label"
                    >電話:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      id="phone"
                      class="form-control form-control-md w-50"
                      name="phone"
                      maxlength="10"
                      pattern="^09\\d{8}"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="birthday" class="col-sm-2 col-form-label"
                    >生日:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="date"
                      id="birthday"
                      class="form-control form-control-md w-50"
                      name="birthday"
                      min="1900-01-01"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="account" class="col-sm-2 col-form-label"
                    >帳號:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control form-control-md w-75"
                      name="account"
                      maxlength="50"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label for="password" class="col-sm-2 col-form-label"
                    >密碼:</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control form-control-md w-75"
                      name="password"
                      maxlength="25"
                      required
                    />
                  </div>
                </div>

                <div class="mb-3 d-flex justify-content-center">
                  <button type="submit" class="btn btn-primary mx-2">
                    確認新增
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
