﻿using ShowcaseWP.Common;
using System;
using System.Collections.Generic;
using System.Diagnostics;
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
using AFWinPhone.components;
using AFWinPhone.components.types;
using AFWinPhone.utils;
using ShowcaseWP.utils;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace ShowcaseWP.pages
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class ProfilePage : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();

        public ProfilePage()
        {
            this.InitializeComponent();

            this.navigationHelper = new NavigationHelper(this);
            this.navigationHelper.LoadState += this.NavigationHelper_LoadState;
            this.navigationHelper.SaveState += this.NavigationHelper_SaveState;


            //show loading indicator
            var progressbar = StatusBar.GetForCurrentView().ProgressIndicator;
            progressbar.Text = Localization.translate("please.wait");
            progressbar.ShowAsync();


            Dictionary<string, string> securityConstraints = ShowcaseUtils.getUserCredentials();

            try
            {
                AFForm profileForm = (AFForm)AfWindowsPhone.getInstance().getFormBuilder()
                    .initBuilder(ShowcaseConstants.PROFILE_FORM, "connection.xml",
                        ShowcaseConstants.PROFILE_FORM_CONNECTION_KEY, securityConstraints).createComponent();

                ContentRoot.Children.Add(profileForm.getView());
            }
            catch (Exception ex)
            {
                ShowcaseUtils.showComponentBuildFailedDialog();
                Debug.WriteLine(ex.StackTrace);
                progressbar.HideAsync();
                return;
            }

            var updateButton = new Button();
            updateButton.Content = Localization.translate("btn.update");
            updateButton.Click += UpdateButtonOnClick;
            var resetButton = new Button();
            resetButton.Content = Localization.translate("btn.reset");
            resetButton.Click += ResetButtonOnClick;
            var buttons = new StackPanel();
            buttons.Orientation = Orientation.Horizontal;
            buttons.HorizontalAlignment = HorizontalAlignment.Center;
            buttons.Children.Add(updateButton);
            buttons.Children.Add(resetButton);
            ContentRoot.Children.Add(buttons);

            //hide loading indicator
            progressbar.HideAsync();
        }

        private void ResetButtonOnClick(object sender, RoutedEventArgs routedEventArgs)
        {
            if (AfWindowsPhone.getInstance().getCreatedComponents().ContainsKey(ShowcaseConstants.PROFILE_FORM))
            {
                AFForm form =
                    (AFForm)AfWindowsPhone.getInstance().getCreatedComponents()[ShowcaseConstants.PROFILE_FORM];
                form.resetData();
            }
        }

        private async void UpdateButtonOnClick(object sender, RoutedEventArgs routedEventArgs)
        {
            if (AfWindowsPhone.getInstance().getCreatedComponents().ContainsKey(ShowcaseConstants.PROFILE_FORM))
            {
                AFForm form =
                    (AFForm)AfWindowsPhone.getInstance().getCreatedComponents()[ShowcaseConstants.PROFILE_FORM];
                if (form.validateData())
                {
                    try
                    {
                        var progressbar = StatusBar.GetForCurrentView().ProgressIndicator;
                        progressbar.Text = Localization.translate("please.wait");
                        await progressbar.ShowAsync();
                        await form.sendData();
                        await progressbar.HideAsync();
                        await new MessageDialog(Localization.translate("person.updateSuccess")).ShowAsync();
                        Frame.GoBack();
                        Frame.GoForward();
                    }
                    catch (Exception ex)
                    {
                        await new MessageDialog(Localization.translate("person.updateFailed")).ShowAsync();
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
