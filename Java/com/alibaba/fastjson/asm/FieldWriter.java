package com.alibaba.fastjson.asm;

final class FieldWriter
  implements FieldVisitor
{
  private final int access;
  private final int desc;
  private final int name;
  FieldWriter next;
  
  FieldWriter(ClassWriter paramClassWriter, int paramInt, String paramString1, String paramString2)
  {
    if (paramClassWriter.firstField == null) {
      paramClassWriter.firstField = this;
    }
    for (;;)
    {
      paramClassWriter.lastField = this;
      this.access = paramInt;
      this.name = paramClassWriter.newUTF8(paramString1);
      this.desc = paramClassWriter.newUTF8(paramString2);
      return;
      paramClassWriter.lastField.next = this;
    }
  }
  
  int getSize()
  {
    return 8;
  }
  
  void put(ByteVector paramByteVector)
  {
    int i = 0x60000 | (0x40000 & this.access) / 64;
    paramByteVector.putShort(this.access & (i ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.desc);
    paramByteVector.putShort(0);
  }
  
  public void visitEnd() {}
}


/* Location:              C:\Users\Anj\Desktop\Clone Test\dex2jar-0.0.9.15\classes_dex2jar.jar!\com\alibaba\fastjson\asm\FieldWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */