using AFWindowsPhone.rest.connection;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Windows.Security.Cryptography;
using Windows.Storage.Streams;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml.Controls;
using Windows.Web.Http;

namespace AFWindowsPhone.rest
{
    class RequestTask
    {
        HeaderType headerType;
        HttpMethod httpMethod;
        ConnectionSecurity security;
        String address;
        Object data;

        public RequestTask(HttpMethod method, HeaderType headerType,
                           ConnectionSecurity security, Object data, String url)
        {
            this.headerType = headerType;
            this.httpMethod = method;
            this.security = security;
            this.address = url;
            this.data = data;
        }

        public async Task<String> doRequest()
        {
            //Show progress indicator
            var statusBar = StatusBar.GetForCurrentView().ProgressIndicator;
            statusBar.Text = Localization.translate("please.wait");
            await statusBar.ShowAsync();

            HttpClient httpClient = new HttpClient();
            var cancellationTokenSource = new CancellationTokenSource(5000);
            
            //ContentRoot.Children.Add(ring); //TODO add ring
            if (security != null)
            {
                if (security.getMethod().Equals(SecurityMethod.BASIC))
                {
                    IBuffer buffer = CryptographicBuffer.ConvertStringToBinary((security.getUserName() + ":" + security.getPassword()), BinaryStringEncoding.Utf8);
                    String encoded = CryptographicBuffer.EncodeToBase64String(buffer);
                    httpClient.DefaultRequestHeaders.Authorization = new Windows.Web.Http.Headers.HttpCredentialsHeaderValue("Basic", encoded);
                    Debug.WriteLine("SECURITY " + "Basic " + encoded);
                }
            }

            String data = "";
            if (data != null && (httpMethod.Equals(HttpMethod.Post) || httpMethod.Equals(HttpMethod.Put)))
            {
                Debug.WriteLine("DATA " + data.ToString());
                data = data.ToString();
            }

          
            HttpStringContent content = new HttpStringContent(data, Windows.Storage.Streams.UnicodeEncoding.Utf8, headerType.ToString());
            HttpRequestMessage htm = new HttpRequestMessage(httpMethod, new Uri(address));
            htm.Content = content;
            HttpResponseMessage response = await httpClient.SendRequestAsync(htm).AsTask(cancellationTokenSource.Token);

            int responseCode = (int) response.StatusCode;
            String responseMsg = response.ReasonPhrase;
            Debug.WriteLine("RESPONSE CODE " + responseCode);
            if (responseCode < 200 || responseCode >= 300)
            {
                throw new Exception(responseCode + " " + responseMsg);
            }

            //hide progress indicator
            await statusBar.HideAsync();
            return await response.Content.ReadAsStringAsync();         
        }

    }
}
