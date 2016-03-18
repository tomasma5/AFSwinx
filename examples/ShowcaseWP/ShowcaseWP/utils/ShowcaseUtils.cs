using System;
using System.Collections.Generic;
using Windows.Storage;
using Windows.UI.Popups;
using AFWinPhone.utils;

namespace ShowcaseWP.utils
{
    internal class ShowcaseUtils
    {
        public static void setUserInPreferences(string username, string password)
        {
            var localSettings = ApplicationData.Current.LocalSettings;
            localSettings.Values["username"] = username;
            localSettings.Values["password"] = password;
        }

        public static void clearUserInPreferences()
        {
            var localSettings = ApplicationData.Current.LocalSettings;
            localSettings.Values.Remove("username");
            localSettings.Values.Remove("password");
        }

        public static Dictionary<string, string> getUserCredentials()
        {
            var localSettings = ApplicationData.Current.LocalSettings;
            var username = (string) localSettings.Values["username"];
            var password = (string) localSettings.Values["password"];
            if (username != null && password != null)
            {
                var result = new Dictionary<string, string>();
                result.Add("username", username);
                result.Add("password", password);
                return result;
            }
            return null;
        }

        public static string getUserLogin()
        {
            var localSettings = ApplicationData.Current.LocalSettings;
            var username = (string) localSettings.Values["username"];
            return username;
        }

        public static async void showComponentBuildFailedDialog()
        {
            await new MessageDialog(Localization.translate("build.failed")).ShowAsync();
        }
    }
}