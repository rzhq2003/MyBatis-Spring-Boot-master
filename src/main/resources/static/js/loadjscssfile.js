//��̬���� js /css 
function loadjscssfile(filename, filetype) {
    if (filetype == "js") { //�ж��ļ�����
        var fileref = document.createElement('script')//������ǩ
        fileref.setAttribute("type", "text/javascript")//��������type��ֵΪtext/javascript
        fileref.setAttribute("src", filename)//�ļ��ĵ�ַ
    }
    else if (filetype == "css") { //�ж��ļ�����
        var fileref = document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    }
    if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
}