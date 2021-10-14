function controllaMail(mail)
{
  if (mail.length<6) return true;
  if ((mail.indexOf('@')==-1) || (mail.indexOf('@')==0)
         || (mail.indexOf('.')==-1)) return true;
  mail=mail.substr(mail.indexOf('@')+1);
  if (mail.indexOf('@')!=-1) return true;
  if ((mail.indexOf('.')==-1) || (mail.indexOf('.')==0)) return true;
  return false;
}
