package FrontEnd;

/**
* FrontEnd/FrontEndInterfaceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FrontEnd.idl
* Friday, November 8, 2013 8:12:02 PM EST
*/

public final class FrontEndInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public FrontEnd.FrontEndInterface value = null;

  public FrontEndInterfaceHolder ()
  {
  }

  public FrontEndInterfaceHolder (FrontEnd.FrontEndInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = FrontEnd.FrontEndInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    FrontEnd.FrontEndInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return FrontEnd.FrontEndInterfaceHelper.type ();
  }

}