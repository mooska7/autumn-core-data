rem ����д��Ĳֿ�·��
set REPOSITORY_PATH=D:\m2_repo
rem ��������...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*lastUpdated*"') do (
    del /s /q %%i
)
rem �������
pause 