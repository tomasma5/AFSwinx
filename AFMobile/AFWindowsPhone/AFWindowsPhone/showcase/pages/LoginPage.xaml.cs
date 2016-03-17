using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.Common;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
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
using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.enums;
using AFWindowsPhone.showcase;
using AFWindowsPhone.showcase.skins;
using AFWindowsPhone.utils;

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

            StatusBarProgressIndicator progressbar = StatusBar.GetForCurrentView().ProgressIndicator;
            progressbar.Text = Localization.translate("please.wait");
            progressbar.ShowAsync();

            CommandBar commandBar = new CommandBar();
            AppBarButton cz = new AppBarButton();
            AppBarButton en = new AppBarButton();
            cz.Label = "Čeština";
            cz.Click += async (sender, args) =>
            {
                
                await progressbar.ShowAsync();
                var previousCacheSize = Frame.CacheSize;
                Frame.CacheSize = 0;
                Localization.changeLanguage(SupportedLanguages.CZ);
                await Task.Delay(100);
                Frame.Navigate(GetType());
                if (this.Frame.CanGoBack)
                {
                    this.Frame.BackStack.RemoveAt(0);
                }
                Frame.CacheSize = previousCacheSize;
                await progressbar.HideAsync();


            };
            en.Label = "English";
            en.Click += async (sender, args) =>
            {
                await progressbar.ShowAsync();
                var previousCacheSize = Frame.CacheSize;
                Frame.CacheSize = 0;
                Localization.changeLanguage(SupportedLanguages.EN);
                await Task.Delay(100);
                Frame.Navigate(GetType());
                if (this.Frame.CanGoBack)
                {
                    this.Frame.BackStack.RemoveAt(0);
                }
                Frame.CacheSize = previousCacheSize;
                await progressbar.HideAsync();
            };
            commandBar.SecondaryCommands.Add(cz);
            commandBar.SecondaryCommands.Add(en);
            BottomAppBar = commandBar;
            
      
            AFForm login = (AFForm) AFWindowsPhone.getInstance().getFormBuilder().initBuilder(ShowcaseConstants.LOGIN_FORM, "connection.xml", ShowcaseConstants.LOGIN_FORM_CONNECTION_KEY).setSkin(new LoginFormSkin()).createComponent();
            ContentRoot.Children.Add(login.getView());
            Button loginBtn = new Button();
            loginBtn.Content = Localization.translate("btn.login");
            loginBtn.HorizontalAlignment = HorizontalAlignment.Right;
            loginBtn.Click += LoginBtn_Click;
            ContentRoot.Children.Add(loginBtn);

            progressbar.HideAsync();
        }

        

        private void doLogin(AFForm form)
        {

            AFField usernameField = form.getFieldById("username");
            AFField passwordField = form.getFieldById("password");
            if (usernameField != null && passwordField != null)
            {
                //save user to shared preferences
                String username = (String)form.getDataFromFieldWithId("username");
                String password = (String)form.getDataFromFieldWithId("password");
                ShowcaseUtils.setUserInPreferences(username, password);
                
                //change content
                Frame.Navigate(typeof(WelcomePage)); 
                if (this.Frame.CanGoBack)
                {
                    this.Frame.BackStack.RemoveAt(0);
                }
            }
            //success
        }

        private async void LoginBtn_Click(object sender, RoutedEventArgs e)
        {
            if (AFWindowsPhone.getInstance().getCreatedComponents().ContainsKey(ShowcaseConstants.LOGIN_FORM)) { 
                AFForm form = (AFForm) AFWindowsPhone.getInstance().getCreatedComponents()[ShowcaseConstants.LOGIN_FORM];
                if(form.validateData())
                {
                    try {

                        StatusBarProgressIndicator progressbar = StatusBar.GetForCurrentView().ProgressIndicator;
                        progressbar.Text = Localization.translate("please.wait");
                        await progressbar.ShowAsync();
                        await form.sendData();
                        doLogin(form);
                        await progressbar.HideAsync();
                    }
                    catch (Exception ex)
                    {
                        await new MessageDialog("Login failed").ShowAsync();
                        Debug.WriteLine(ex.StackTrace);
                    }
                }
            }
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
