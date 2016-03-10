using AFWindowsPhone.rest.connection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;
using Windows.Web.Http;

namespace AFWindowsPhone.rest
{
    class RequestTask
    {
        ProgressRing ring;
        HeaderType headerType;
        Windows.Web.Http.HttpMethod httpMethod;
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

            //TODO vymyslet co s timhle
            ProgressRing ring = new ProgressRing();
            ring.IsActive = true;
            ring.Width = 20;
            ring.Height = 20;
        }

        public async Task<String> doRequest()
        {
            //ContentRoot.Children.Add(ring); //TODO add ring

            HttpClient httpClient = new HttpClient();
            var cancellationTokenSource = new CancellationTokenSource(5000);
            HttpStringContent content = new HttpStringContent("", Windows.Storage.Streams.UnicodeEncoding.Utf8, "application/json");
            HttpRequestMessage htm = new HttpRequestMessage(httpMethod, new Uri(address));
            htm.Content = content;
            HttpResponseMessage response = await httpClient.SendRequestAsync(htm).AsTask(cancellationTokenSource.Token);
            return await response.Content.ReadAsStringAsync();         
        }

    }
}
