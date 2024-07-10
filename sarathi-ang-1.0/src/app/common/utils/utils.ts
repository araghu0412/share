import { JSEncrypt } from "jsencrypt";
import { environment } from 'src/environments/environment';

const monthMap: Map<any, any> = new Map();
monthMap.set(0, "JAN");
monthMap.set(1, "FEB");
monthMap.set(2, "MAR");
monthMap.set(3, "APR");
monthMap.set(4, "MAY");
monthMap.set(5, "JUN");
monthMap.set(6, "JUL");
monthMap.set(7, "AUG");
monthMap.set(8, "SEP");
monthMap.set(9, "OCT");
monthMap.set(10, "NOV");
monthMap.set(11, "DEC");
monthMap.set("JAN", 0);
monthMap.set("FEB", 1);
monthMap.set("MAR", 2);
monthMap.set("APR", 3);
monthMap.set("MAY", 4);
monthMap.set("JUN", 5);
monthMap.set("JUL", 6);
monthMap.set("AUG", 7);
monthMap.set("SEP", 8);
monthMap.set("OCT", 9);
monthMap.set("NOV", 10);
monthMap.set("DEC", 11);

export function dataEncryption(data: string): any {
  if (data === null || data === "") {
      return data;
  }

  let jsEcnrypt = new JSEncrypt();
  jsEcnrypt.setPublicKey(environment.PUBLIC_KEY);

  return jsEcnrypt.encrypt(data);
}

export function getDefaultDate(defaultDate: string): string {
  if (defaultDate) {
    return validateDefaultDate(defaultDate);
  } else {
    return getTodaysDateInString(new Date());
  }
}

function validateDefaultDate(defaultDate: string) {
  let date = new Date(defaultDate);
  if (date.toString() === "Invalid Date" || monthMap.get(date.getMonth()) !== defaultDate.split("-")[1]) {
    return getTodaysDateInString(new Date())
  } else {
    let splitselectedFullDate = defaultDate.split("-");
    return splitselectedFullDate[0] + "-" + splitselectedFullDate[1] + "-" + splitselectedFullDate[2];
  }
}

function getTodaysDateInString(date: Date): string {
  return date.getDate().toString() + "-" + monthMap.get(date.getMonth()) + "-" + date.getFullYear().toString();
}
