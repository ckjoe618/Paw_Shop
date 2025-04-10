const memberRequest = axios.create({
  baseURL: window.location.origin,
});

const apiFindMemberAll = () => memberRequest.get("/findAll");
const apiFindMemberType = (data) =>
  memberRequest.post("/findType", { memberId: data });
const apiAddMember = (data) => memberRequest.post("/add", data);
const apiUpdateMember = (data) => memberRequest.post("/update", data);
const apiDeleteMember = (data) =>
  memberRequest.post("/delete", { memberId: data });
