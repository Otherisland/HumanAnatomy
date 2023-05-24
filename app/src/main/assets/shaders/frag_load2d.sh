#version 300 es
precision mediump float;
in float vxPosition;
in vec2 vTextureCoord; //接收从顶点着色器过来的参数
uniform sampler2D sTexture;//纹理内容数据
uniform float xPosition;
out vec4 fragColor;
void main()
{
        float ff=xPosition;
       if(vxPosition+0.34>=-0.27&&vxPosition+0.5<ff/493.0){
               fragColor = vec4(0.8,0.3,0.3,0.9);
        }else if(vxPosition+0.5>=ff/493.0 && vxPosition<=1.0){
              // fragColor = texture(sTexture, vTextureCoord);
              vec4 bcolor = texture(sTexture, vTextureCoord);
              if(bcolor.a<0.6f) {//去黑边
                       discard;
              } else {
                    fragColor=bcolor;
              }
        }
}              