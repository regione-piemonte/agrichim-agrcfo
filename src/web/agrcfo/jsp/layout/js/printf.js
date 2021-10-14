function printf(format) {
   document.write(_spr(format, arguments));
}


function sprintf(format) {
   return _spr(format, arguments);
}


function _spr(format, args) {
   function isdigit(c) {
      return (c <= "9") && (c >= "0");
   }

   function rep(c, n) {
      var s = "";
      while (--n >= 0)
         s += c;
      return s;
   }

   var c;
   var i, ii, j = 1;
   var retstr = "";
   var space = "&nbsp;";
   
   
   for (i = 0; i < format.length; i++) {
      var buf = "";
      var segno = "";
      var expx = "";
      c = format.charAt(i);
      if (c == "\n") {
         c = "<br>";
      }
      if (c == "%") {
         i++;
         leftjust = false;
         if (format.charAt(i) == '-') {
            i++;
            leftjust = true;
         }
         padch = ((c = format.charAt(i)) == "0") ? "0" : space;
         if (c == "0")
            i++;
         field = 0;
         if (isdigit(c)) {
            field = parseInt(format.substring(i));
            i += String(field).length;
         }
   
         if ((c = format.charAt(i)) == '.') {
            digits = parseInt(format.substring(++i));
            i += String(digits).length;
            c = format.charAt(i);
         }
         else
            digits = 0;
   
         switch (c.toLowerCase()) {
            case "x":
               buf = args[j++].toString(16);
               break;
            case "e":
               expx = -1;
            case "d":
               if (args[j] < 0) {
                  args[j] = -args[j];
                  segno = "-";
                  field--;
               }
               if (expx != "") {
                  with (Math)
                     expx = floor(log(args[j]) / LN10);
                  args[j] /= Number("1E" + expx);
                  field -= String(expx).length + 2;
               }
               var x = args[j++];
               for (ii=0; ii < digits && x - Math.floor(x); ii++)
                  x *= 10;
               
               x = String(Math.round(x));
               
               x = rep("0", ii - x.length + 1) + x;
               
               buf += x.substring(0, x.length - ii);
               
               if (digits > 0)
                  buf += "." + x.substring(x.length - ii) + rep("0", digits - ii);
               if (expx != "") {
                  var expsign = (expx >= 0) ? "+" : "-";
                  expx = Math.abs(expx) + "";
                  buf += c + expsign + rep("0", 3 - expx.length) + expx;
               }
               break;
            case "o":
               buf = args[j++].toString(8);
               break;
            case "s":
               buf = args[j++];
               break;
            case "c":
               buf = args[j++].substring(0, 1);
               break;
            default:
               retstr += c;
         }
         field -= buf.length;
         if (!leftjust) {
            if (padch == space)
               retstr += rep(padch, field) + segno;
            else
               retstr += segno + rep("0", field);
         }
         retstr += buf;
         if (leftjust)
            retstr += rep(space, field);
      }
      else
         retstr += c;
   }
   return retstr;
}
