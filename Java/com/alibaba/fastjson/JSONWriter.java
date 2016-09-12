package com.alibaba.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter
{
  private Context context;
  private final Writer out;
  private JSONSerializer serializer;
  private SerializeWriter writer;
  
  public JSONWriter(Writer paramWriter)
  {
    this.out = paramWriter;
    this.writer = new SerializeWriter();
    this.serializer = new JSONSerializer(this.writer);
  }
  
  public void close()
    throws IOException
  {
    if (this.writer.size() != 0) {
      flush();
    }
  }
  
  public void flush()
    throws IOException
  {
    this.writer.writeTo(this.out);
    this.writer = new SerializeWriter();
    this.serializer = new JSONSerializer(this.writer);
  }
  
  public void writeEndArray()
  {
    this.writer.write(']');
    this.context = this.context.getParent();
    if (this.context == null) {}
    do
    {
      return;
      if (this.context.getState() == State.PropertyKey)
      {
        this.context.setState(State.PropertyValue);
        return;
      }
      if (this.context.getState() == State.BeginArray)
      {
        this.context.setState(State.ArrayValue);
        return;
      }
    } while (this.context.getState() != State.ArrayValue);
  }
  
  public void writeEndObject()
  {
    this.writer.write('}');
    this.context = this.context.getParent();
    if (this.context == null) {}
    do
    {
      return;
      if (this.context.getState() == State.PropertyKey)
      {
        this.context.setState(State.PropertyValue);
        return;
      }
      if (this.context.getState() == State.BeginArray)
      {
        this.context.setState(State.ArrayValue);
        return;
      }
    } while (this.context.getState() != State.ArrayValue);
  }
  
  public void writeKey(String paramString)
  {
    if (this.context.getState() == State.PropertyValue) {
      this.writer.write(',');
    }
    this.writer.writeString(paramString);
    this.context.setState(State.PropertyKey);
  }
  
  public void writeStartArray()
  {
    if (this.context == null)
    {
      this.context = new Context(null, State.BeginArray);
      this.writer.write('[');
      return;
    }
    if (this.context.getState() == State.PropertyKey) {
      this.writer.write(':');
    }
    label100:
    do
    {
      for (;;)
      {
        this.context = new Context(this.context, State.BeginArray);
        break;
        if (this.context.getState() != State.ArrayValue) {
          break label100;
        }
        this.writer.write(',');
      }
    } while (this.context.getState() == State.BeginArray);
    throw new JSONException("illegal state : " + this.context.getState());
  }
  
  public void writeStartObject()
  {
    if (this.context == null)
    {
      this.context = new Context(null, State.BeginObject);
      this.writer.write('{');
      return;
    }
    if (this.context.getState() == State.PropertyKey) {
      this.writer.write(':');
    }
    label100:
    do
    {
      for (;;)
      {
        this.context = new Context(this.context, State.BeginObject);
        break;
        if (this.context.getState() != State.ArrayValue) {
          break label100;
        }
        this.writer.write(',');
      }
    } while ((this.context.getState() == State.BeginObject) || (this.context.getState() == State.BeginArray));
    throw new JSONException("illegal state : " + this.context.getState());
  }
  
  public void writeValue(Object paramObject)
  {
    if (this.context.getState() == State.PropertyKey) {
      this.writer.write(':');
    }
    this.serializer.write(paramObject);
    this.context.setState(State.PropertyValue);
  }
  
  public static class Context
  {
    private final Context parent;
    private JSONWriter.State state;
    
    public Context(Context paramContext, JSONWriter.State paramState)
    {
      this.parent = paramContext;
      this.state = paramState;
    }
    
    public Context getParent()
    {
      return this.parent;
    }
    
    public JSONWriter.State getState()
    {
      return this.state;
    }
    
    public void setState(JSONWriter.State paramState)
    {
      this.state = paramState;
    }
  }
  
  public static enum State
  {
    static
    {
      BeginArray = new State("BeginArray", 3);
      ArrayValue = new State("ArrayValue", 4);
      State[] arrayOfState = new State[5];
      arrayOfState[0] = BeginObject;
      arrayOfState[1] = PropertyKey;
      arrayOfState[2] = PropertyValue;
      arrayOfState[3] = BeginArray;
      arrayOfState[4] = ArrayValue;
      $VALUES = arrayOfState;
    }
    
    private State() {}
  }
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\JSONWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */