/**
 * Copyright [2017] [Yahya Hassanzadeh-Nazarabadi]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
public class Main
{
    static final int KUSIS_ID = 60326;
    static final int DD_MM_YY = 210498;

    public static void main(String args[])
    {
        SSLProvider sslProvider = new SSLProvider(4444);
        sslProvider.start();

        // ID + Date divided to available port number and 1024 added to be safe
        int portNumber = 1024 + ((KUSIS_ID + DD_MM_YY) % 65535);
        SSLServer sslServer = new SSLServer(portNumber);
        sslServer.start();
    }

}
