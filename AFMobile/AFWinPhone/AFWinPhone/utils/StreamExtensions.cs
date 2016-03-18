using System;
using System.Threading.Tasks;
using Windows.Storage.Streams;

namespace AFWinPhone.utils
{
    public static class StreamExtensions
    {
        /// <summary>
        /// Reads string from the stream asynchronously.
        /// </summary>
        /// <param name="stream"><see cref="IRandomAccessStream"/> instance to read from</param>
        /// <returns>string read from the stream</returns>
        public static async Task<string> ReadStringAsync(this IRandomAccessStream stream)
        {
            var inputStream = stream.GetInputStreamAt(0);
            using (DataReader dr = new DataReader(inputStream))
            {
                dr.UnicodeEncoding = Windows.Storage.Streams.UnicodeEncoding.Utf8;
                uint bytesLoaded = await dr.LoadAsync((uint)stream.Size);
                string outString = dr.ReadString(bytesLoaded);

                dr.DetachStream();
                return outString;
            }
        }
    }
}
