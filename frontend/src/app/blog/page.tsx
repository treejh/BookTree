import Image from 'next/image'
import Link from 'next/link'

interface Post {
    id: number
    category: string
    title: string
    description: string
    date: string
    views: number
    comments: number
}

interface Category {
    name: string
    count: number
}

const categories: Category[] = [
    { name: '전체보기', count: 86 },
    { name: '형영 플러스', count: 12 },
    { name: '인턴일지', count: 11 },
    { name: 'WIL', count: 10 },
    { name: '프로그래머스', count: 6 },
    { name: '지원여정', count: 5 },
    { name: '우아한테크코스', count: 5 },
    { name: 'vue.js', count: 5 },
    { name: 'programmers', count: 4 },
    { name: 'Vue.js 오류', count: 4 },
    { name: 'JavaScript', count: 4 },
    { name: '회고', count: 4 },
    { name: '개발일지', count: 4 },
]

const posts: Post[] = [
    {
        id: 1,
        category: 'React',
        title: 'React Query로 상태관리 최적화하기',
        description:
            'React Query를 활용한 서버 상태 관리와 캐싱 전략, 실시간 데이터 동기화 방법에 대해 설명해 드립니다.',
        date: '2024년 3월 15일',
        views: 1234,
        comments: 23,
    },
    {
        id: 2,
        category: 'TypeScript',
        title: '타입스크립트로 안전한 API 개발',
        description: '타입스크립트를 활용하여 백엔드 API와 프론트엔드 통신을 타입 안전하게 구현하는 방법을 공유합니다.',
        date: '2024년 3월 12일',
        views: 987,
        comments: 15,
    },
    {
        id: 3,
        category: 'NextJS',
        title: 'Next.js와 GraphQL 통합하기',
        description:
            'Next.js 프로젝트에서 GraphQL을 효율적으로 통합하고 활용하는 방법에 대한 실전 가이드를 제공합니다.',
        date: '2024년 3월 10일',
        views: 856,
        comments: 19,
    },
]

export default function BlogPage() {
    return (
        <div className="flex gap-8 max-w-8xl mx-auto px-4 py-8">
            {/* 메인 컨텐츠 */}
            <main className="flex-1 pl-100">
                <div className="bg-white rounded-xl shadow-lg p-8">
                    {/* 프로필 섹션 */}
                    <section className="text-center mb-12">
                        <h1 className="text-3xl font-bold mb-2">Minsu Kim&apos;s Dev Journal</h1>
                        <p className="text-gray-600 mb-4">@devkim</p>
                        <p className="text-gray-600 mb-6">A space to document a web developer&apos;s growth journey.</p>

                        <div className="flex justify-center gap-8">
                            <Link href="/follow" className="text-center hover:opacity-80">
                                <div className="text-xl font-bold">125</div>
                                <div className="text-gray-600">팔로잉</div>
                            </Link>
                            <Link href="/follow" className="text-center hover:opacity-80">
                                <div className="text-xl font-bold">238</div>
                                <div className="text-gray-600">팔로워</div>
                            </Link>
                            <div className="text-center">
                                <div className="text-xl font-bold">42</div>
                                <div className="text-gray-600">포스트</div>
                            </div>
                        </div>

                        <div className="mt-6">
                            <button className="bg-[#2E804E] text-white px-4 py-2 rounded-md hover:bg-[#247040] transition-colors">
                                팔로우
                            </button>
                        </div>
                    </section>

                    {/* 네비게이션 */}
                    <nav className="border-b border-gray-200 mb-8">
                        <ul className="flex gap-8">
                            <li className="pb-2 border-b-2 border-gray-900">
                                <Link href="/blog" className="text-gray-900">
                                    최신순
                                </Link>
                            </li>
                            <li className="pb-2">
                                <Link href="/blog/popular" className="text-gray-600">
                                    인기순
                                </Link>
                            </li>
                            <li className="pb-2">
                                <Link href="/blog/bookmarks" className="text-gray-600">
                                    북마크
                                </Link>
                            </li>
                        </ul>
                    </nav>

                    {/* 새 글 작성 버튼 */}
                    <div className="flex justify-end mb-8">
                        <Link href="/post/write">
                            <button className="bg-[#2E804E] text-white px-4 py-2 rounded-md hover:bg-[#247040] transition-colors flex items-center gap-2">
                                <span>새 글 작성하기</span>
                            </button>
                        </Link>
                    </div>

                    {/* 블로그 포스트 목록 */}
                    <div className="space-y-8">
                        {posts.map((post) => (
                            <article
                                key={post.id}
                                className="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow"
                            >
                                <div className="flex items-center gap-2 mb-2">
                                    <span className="bg-gray-100 px-2 py-1 rounded text-sm">{post.category}</span>
                                    <span className="text-gray-500 text-sm">{post.date}</span>
                                </div>
                                <h2 className="text-xl font-bold mb-2">{post.title}</h2>
                                <p className="text-gray-600 mb-4">{post.description}</p>
                                <div className="flex items-center gap-4 text-sm text-gray-500">
                                    <span>조회 {post.views}</span>
                                    <span>댓글 {post.comments}</span>
                                </div>
                            </article>
                        ))}
                    </div>

                    {/* 페이지네이션 */}
                    <div className="flex justify-center gap-2 mt-8">
                        <button className="px-4 py-2 border rounded hover:bg-gray-50">Previous</button>
                        <button className="px-4 py-2 border rounded bg-gray-900 text-white">1</button>
                        <button className="px-4 py-2 border rounded hover:bg-gray-50">2</button>
                        <button className="px-4 py-2 border rounded hover:bg-gray-50">3</button>
                        <button className="px-4 py-2 border rounded hover:bg-gray-50">Next</button>
                    </div>
                </div>
            </main>

            {/* 카테고리 사이드바 */}
            <aside className="w-64 flex-shrink-0 mt-100">
                <div className="bg-white rounded-xl shadow-lg p-6">
                    <h2 className="text-xl font-bold mb-4">카테고리 목록</h2>
                    <div className="border-b border-gray-200 mb-4"></div>
                    <ul className="space-y-2">
                        {categories.map((category) => (
                            <li key={category.name}>
                                <Link
                                    href={`/blog/category/${category.name}`}
                                    className="flex justify-between items-center text-gray-700 hover:text-gray-900"
                                >
                                    <span>{category.name}</span>
                                    <span className="text-gray-500">({category.count})</span>
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>
            </aside>
        </div>
    )
}
