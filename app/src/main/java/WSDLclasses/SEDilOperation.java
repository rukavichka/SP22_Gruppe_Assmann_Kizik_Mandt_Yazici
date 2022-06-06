package WSDLclasses;
//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 6.0.1.1
//
// Created by Quasar Development 
//
//----------------------------------------------------


import java.util.Hashtable;
import org.ksoap2.serialization.*;

public  class SEDilOperation extends AttributeContainer implements KvmSerializable
{

    
    private transient java.lang.Object __source;    
    
    public Integer ops_id=0;
    
    public String operation;
    
    public String description;

    

    public void loadFromSoap(java.lang.Object paramObj,SEDExtendedSoapSerializationEnvelope __envelope)
    {
        if (paramObj == null)
            return;
        AttributeContainer inObj=(AttributeContainer)paramObj;
        __source=inObj; 
        
        if(inObj instanceof SoapObject)
        {
            SoapObject soapObject=(SoapObject)inObj;
            int size = soapObject.getPropertyCount();
            for (int i0=0;i0< size;i0++)
            {
                PropertyInfo info=soapObject.getPropertyInfo(i0);
                if(!loadProperty(info,soapObject,__envelope))
                {
                }
            } 
        }


    }

    
    protected boolean loadProperty(PropertyInfo info,SoapObject soapObject,SEDExtendedSoapSerializationEnvelope __envelope)
    {
        java.lang.Object obj = info.getValue();
        if (info.name.equals("ops_id"))
        {
            if(obj!=null)
            {
                if (obj instanceof SoapPrimitive)
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.ops_id = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.ops_id = (Integer)obj;
                }
            }
            return true;
        }
        if (info.name.equals("operation"))
        {
            if(obj!=null)
            {
                if (obj instanceof SoapPrimitive)
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.operation = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.operation = (String)obj;
                }
                else{
                    this.operation = "";
                }
            }
            return true;
        }
        if (info.name.equals("description"))
        {
            if(obj!=null)
            {
                if (obj instanceof SoapPrimitive)
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.description = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.description = (String)obj;
                }
                else{
                    this.description = "";
                }
            }
            return true;
        }
        return false;
    }    
    public java.lang.Object getOriginalXmlSource()
    {
        return __source;
    }    
    
    @Override
    public java.lang.Object getProperty(int propertyIndex) {
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==0)
        {
            return this.ops_id;
        }
        else if(propertyIndex==1)
        {
            return this.operation;
        }
        else if(propertyIndex==2)
        {
            return this.description;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, Hashtable arg1, PropertyInfo info)
    {
            if(propertyIndex==0)
            {
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ops_id";
                info.namespace= "";
            }
            else if(propertyIndex==1)
            {
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "operation";
                info.namespace= "";
            }
            else if(propertyIndex==2)
            {
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "description";
                info.namespace= "";
            }
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }

    
}
