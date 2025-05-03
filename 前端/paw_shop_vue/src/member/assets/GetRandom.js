// 隨機生成數字，跟 oneClick() 配合
export const getRandomNumber = (length) => {
  const min = Math.pow(10, length - 1);
  const max = Math.pow(10, length) - 1;
  return Math.floor(Math.random() * (max - min + 1)) + min;
};
// 隨機生成名字，跟 oneClick() 配合
export const getRandomName = () => {
  const lastNames = [
    "陳",
    "林",
    "張",
    "李",
    "王",
    "黃",
    "吳",
    "劉",
    "蔡",
    "楊",
  ];
  const firstNames = [
    "志明",
    "書豪",
    "致君",
    "冠宇",
    "心豪",
    "柏翰",
    "承翰",
    "宗翰",
    "家豪",
    "信宏",
    "偉臣",
    "躍居",
    "孟如",
    "致遠",
    "雋洪",
  ];
  const lastName = lastNames[Math.floor(Math.random() * lastNames.length)];
  const firstName = firstNames[Math.floor(Math.random() * firstNames.length)];
  return lastName + firstName;
};
