#version 300 es
precision mediump float;
uniform sampler2D sTexture;//纹理内容数据
//接收从顶点着色器过来的参数
in vec4 ambient;
in vec4 diffuse;
in vec4 specular;
in vec2 vTextureCoord;
uniform vec4 aColor;
uniform int uAlpha;
uniform float uTime;
out vec4 fragColor;//输出到的片元颜色
void main()                         
{
    //将计算出的颜色给此片元
    vec4 finalColor=texture(sTexture, vTextureCoord);
    finalColor.xyz=finalColor.xyz*aColor.xyz;
    finalColor.w=finalColor.w*uTime;

   //vec4 finalColor=aColor;
   //给此片元颜色值
   fragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;
   if(uAlpha==1)
   {
       fragColor.w=fragColor.w*0.3f;
   }
}   