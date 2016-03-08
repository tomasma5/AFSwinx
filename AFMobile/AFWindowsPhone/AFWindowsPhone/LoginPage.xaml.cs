using AFWindowsPhone.Common;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.Popups;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Windows.Web.Http;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace AFWindowsPhone
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class LoginPage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();

        public LoginPage()
        {
            this.InitializeComponent();

            this.navigationHelper = new NavigationHelper(this);
            this.navigationHelper.LoadState += this.NavigationHelper_LoadState;
            this.navigationHelper.SaveState += this.NavigationHelper_SaveState;

            StackPanel userNamePanel = new StackPanel();
            userNamePanel.Orientation = Orientation.Horizontal;
            StackPanel passwordPanel = new StackPanel();
            passwordPanel.Orientation = Orientation.Horizontal;
            TextBlock userNameLabel = new TextBlock();
            userNameLabel.Width = 150;
            userNameLabel.Text = "Username";
            userNameLabel.FontSize = 24;
            TextBlock passwordLabel = new TextBlock();
            passwordLabel.Width = 150;
            passwordLabel.Text = "Password";
            passwordLabel.FontSize = 24;

            TextBox userName = new TextBox();
            userName.Width = 200;
            PasswordBox password = new PasswordBox();
            password.Width = 200;

            userNamePanel.Children.Add(userNameLabel);
            userNamePanel.Children.Add(userName);
            passwordPanel.Children.Add(passwordLabel);
            passwordPanel.Children.Add(password);
            ContentRoot.Children.Add(userNamePanel);
            ContentRoot.Children.Add(passwordPanel);

            Button btn = new Button();
            btn.Content = "Sumbit";
            btn.Click += async (sender, args) =>
            {
                String usernameStr = userName.Text;
                String psswd = password.Password;
                MessageDialog dialog = new MessageDialog(usernameStr + " " + psswd, "credentials");
                await dialog.ShowAsync();

                ProgressRing ring = new ProgressRing();
                ring.IsActive = true;
                ring.Width = 20;
                ring.Height = 20;
                ContentRoot.Children.Add(ring);
                HttpClient httpClient = new HttpClient();
                HttpStringContent content = new HttpStringContent("", Windows.Storage.Streams.UnicodeEncoding.Utf8, "application/json");
                HttpRequestMessage htm = new HttpRequestMessage(HttpMethod.Get, new Uri("http://toms-cz.com/AFServer/rest/users/loginForm"));
                htm.Content = content;
                HttpResponseMessage response = await httpClient.SendRequestAsync(htm);
                String seznam = await response.Content.ReadAsStringAsync();
                await new MessageDialog(seznam).ShowAsync();
                ContentRoot.Children.Remove(ring);
            };
            ContentRoot.Children.Add(btn);
        }

        /// <summary>
        /// Gets the <see cref="NavigationHelper"/> associated with this <see cref="Page"/>.
        /// </summary>
        public NavigationHelper NavigationHelper
        {
            get { return this.navigationHelper; }
        }

        /// <summary>
        /// Gets the view model for this <see cref="Page"/>.
        /// This can be changed to a strongly typed view model.
        /// </summary>
        public ObservableDictionary DefaultViewModel
        {
            get { return this.defaultViewModel; }
        }

        /// <summary>
        /// Populates the page with content passed during navigation.  Any saved state is also
        /// provided when recreating a page from a prior session.
        /// </summary>
        /// <param name="sender">
        /// The source of the event; typically <see cref="NavigationHelper"/>
        /// </param>
        /// <param name="e">Event data that provides both the navigation parameter passed to
        /// <see cref="Frame.Navigate(Type, Object)"/> when this page was initially requested and
        /// a dictionary of state preserved by this page during an earlier
        /// session.  The state will be null the first time a page is visited.</param>
        private void NavigationHelper_LoadState(object sender, LoadStateEventArgs e)
        {
        }

        /// <summary>
        /// Preserves state associated with this page in case the application is suspended or the
        /// page is discarded from the navigation cache.  Values must conform to the serialization
        /// requirements of <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="sender">The source of the event; typically <see cref="NavigationHelper"/></param>
        /// <param name="e">Event data that provides an empty dictionary to be populated with
        /// serializable state.</param>
        private void NavigationHelper_SaveState(object sender, SaveStateEventArgs e)
        {
        }

        #region NavigationHelper registration

        /// <summary>
        /// The methods provided in this section are simply used to allow
        /// NavigationHelper to respond to the page's navigation methods.
        /// <para>
        /// Page specific logic should be placed in event handlers for the  
        /// <see cref="NavigationHelper.LoadState"/>
        /// and <see cref="NavigationHelper.SaveState"/>.
        /// The navigation parameter is available in the LoadState method 
        /// in addition to page state preserved during an earlier session.
        /// </para>
        /// </summary>
        /// <param name="e">Provides data for navigation methods and event
        /// handlers that cannot cancel the navigation request.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion
    }
}
